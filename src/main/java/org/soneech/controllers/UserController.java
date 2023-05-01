package org.soneech.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public String userPage() {
        return "user/user_page";
    }

    @GetMapping("/basket")
    public String userBasketPage() {
        return "user/basket";
    }
}
