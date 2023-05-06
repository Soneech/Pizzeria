package org.soneech.controllers;

import org.soneech.models.*;
import org.soneech.repository.BasketDataRepository;
import org.soneech.service.BasketDataService;
import org.soneech.service.OrderService;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class OrderController {
    private final UserService userService;
    private final BasketDataService basketDataService;
    private final BasketDataRepository basketDataRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(UserService userService, BasketDataService basketDataService,
                           BasketDataRepository basketDataRepository, OrderService orderService) {
        this.userService = userService;
        this.basketDataService = basketDataService;
        this.basketDataRepository = basketDataRepository;
        this.orderService = orderService;
    }

    @GetMapping("/orders/active")
    public String activeOrdersPage(Model model, Authentication authentication) {
        List<Order> orders = orderService.getActiveUserOrders(authentication);
        model.addAttribute("orders", orders);
        model.addAttribute("message",
                "Здесь отображаются ваши активные заказы. Мы свяжемся с вами по указанному номеру :)");
        return "/market/orders";
    }

    @GetMapping("/orders/completed")
    public String completedOrders(Model model, Authentication authentication) {
        List<Order> orders = orderService.getCompletedUserOrders(authentication);
        model.addAttribute("orders", orders);
        model.addAttribute("message", "Ваши завершённые заказы");
        return "/market/orders";
    }

    @GetMapping("/orders/new")
    public String createOrderPage(Authentication authentication, Model model) {
        setParametersForCreateOrderPage(model, authentication);
        model.addAttribute("address", new Address());

        return "/market/make_order";
    }

    public void setParametersForCreateOrderPage(Model model, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        List<BasketData> basketData = basketDataRepository.findAllBasketDataForUser(user.getId());
        int totalCost = basketDataService.getTotalCost(basketData);

        model.addAttribute("basketData", basketData);
        model.addAttribute("totalCost", totalCost);
        model.addAttribute("imgExtension", Image.EXTENSION);
        model.addAttribute("user", user);
    }

    @PostMapping("/orders")
    public String createOrder(@ModelAttribute("address") @Valid Address address, BindingResult bindingResult,
                              Model model, Authentication authentication) {
        if (!bindingResult.hasErrors()) {
            orderService.addOrder(address, authentication);
            return "redirect:/user/orders/active";
        }
        setParametersForCreateOrderPage(model, authentication);
        return "/market/make_order";
    }

    public void prepareOrderPage(Long id, Model model) {
        Order order = orderService.getOrderById(id);

        model.addAttribute("order", order);
        model.addAttribute("imgExtension", Image.EXTENSION);
    }

    @GetMapping("/orders/active/{id}")
    public String activeOrder(@PathVariable Long id, Model model) {
        prepareOrderPage(id, model);
        return "/market/order_page";
    }

    @GetMapping("/orders/completed/{id}")
    public String completedOrder(@PathVariable Long id, Model model) {
        prepareOrderPage(id, model);
        return "/market/order_page";
    }
}
