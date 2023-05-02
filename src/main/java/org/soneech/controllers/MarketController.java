package org.soneech.controllers;

import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basket")
public class MarketController {
    private final UserService userService;

    @Autowired
    public MarketController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userBasketPage() {
        return "user/basket";
    }

    @PostMapping("/basket/{pizzaId}")
    public void addPizzaToBasket(@PathVariable("pizzaId") Long pizzaId) {

    }
}
