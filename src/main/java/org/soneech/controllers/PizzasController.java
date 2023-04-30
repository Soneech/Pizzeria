package org.soneech.controllers;

import org.soneech.models.Image;
import org.soneech.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizza")
public class PizzasController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzasController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping("/{id}")
    public String pizzaPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizzaById(id).get());
        model.addAttribute("img_extension", Image.EXTENSION);
        return "pizza/pizza_page";
    }
}
