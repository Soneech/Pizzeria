package org.soneech.controllers;

import org.soneech.models.BasketData;
import org.soneech.models.Image;
import org.soneech.service.BasketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/user")
public class BasketController {
    private final BasketDataService basketDataService;

    @Autowired
    public BasketController(BasketDataService basketDataService) {
        this.basketDataService = basketDataService;
    }

    @GetMapping("/basket")
    public String userBasketPage(Model model, Authentication authentication) {
        List<BasketData> basketData = basketDataService.getAllBasketDataForUser(authentication);
        int totalCost = basketDataService.getTotalCost(basketData);

        model.addAttribute("basketData", basketData);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("imgExtension", Image.EXTENSION);
        return "market/basket";
    }

    @PostMapping("/basket/{id}")
    public String addPizzaToBasket(@PathVariable("id") Long pizzaId, Authentication authentication) {
        basketDataService.saveBasketData(authentication, pizzaId);
        return "redirect:/user/basket";
    }

    @PatchMapping("/basket/increase/{basketDataId}")
    public String increaseProductCount(@PathVariable("basketDataId") Long basketDataId) {
        basketDataService.increasePizzaInBasket(basketDataId);

        return "redirect:/user/basket#" + basketDataId;
    }

    @PatchMapping("/basket/decrease/{basketDataId}")
    public String decreaseProductCount(@PathVariable("basketDataId") Long basketDataId) {
        basketDataService.decreasePizzaInBasket(basketDataId);
        return "redirect:/user/basket#" + basketDataId;
    }

    @DeleteMapping("/basket/delete/{basketDataId}")
    public String deleteProductFromBasket(@PathVariable("basketDataId") Long basketDataId) {
        basketDataService.deletePizzaFromBasket(basketDataId);
        return "redirect:/user/basket";
    }
}
