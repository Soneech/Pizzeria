package org.soneech.controllers;

import org.soneech.models.BasketData;
import org.soneech.service.BasketDataService;
import org.soneech.service.PizzaService;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/user/basket")
public class MarketController {
    private final UserService userService;
    private final PizzaService pizzaService;
    private final BasketDataService basketDataService;

    @Autowired
    public MarketController(UserService userService,
                            PizzaService pizzaService, BasketDataService basketDataService) {
        this.userService = userService;
        this.pizzaService = pizzaService;
        this.basketDataService = basketDataService;
    }

    @GetMapping
    public String userBasketPage(Model model, Authentication authentication) {
        List<BasketData> basketData = basketDataService.getAllBasketDataForUser(authentication);
        int totalCost = basketDataService.getTotalCost(basketData);

        model.addAttribute("basketData", basketData);
        model.addAttribute("totalCost", totalCost);
        return "market/basket";
    }

    @PostMapping("/{id}")
    public String addPizzaToBasket(@PathVariable("id") Long pizzaId, Authentication authentication) {
        basketDataService.saveBasketData(authentication, pizzaId);
        return "redirect:/user/basket";
    }

    @PatchMapping("/increase/{basketDataId}")
    public String increaseProductCount(@PathVariable("basketDataId") Long basketDataId) {
        basketDataService.increasePizzaInBasket(basketDataId);
        return "redirect:/user/basket";
    }

    @PatchMapping("/decrease/{basketDataId}")
    public String decreaseProductCount(@PathVariable("basketDataId") Long basketDataId) {
        basketDataService.decreasePizzaInBasket(basketDataId);
        return "redirect:/user/basket";
    }
}
