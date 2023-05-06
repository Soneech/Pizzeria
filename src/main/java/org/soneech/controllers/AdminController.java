package org.soneech.controllers;

import org.soneech.models.Order;
import org.soneech.service.OrderService;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public AdminController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String adminPage() {
        return "admin/admin_page";
    }

    @GetMapping("/orders/active")
    public String activeOrdersPage(Model model) {
        List<Order> orders = orderService.getActiveOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("message", "Активные заказы");
        return "/market/orders";
    }

    @GetMapping("/orders/completed")
    public String completedOrdersPage(Model model) {
        List<Order> orders = orderService.getCompletedOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("message", "Завершённые заказы");
        return "/market/orders";
    }

    @PostMapping("/orders/close/{id}")
    public String closeOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        order.setIsActive(false);
        orderService.updateOrder(order);
        return "redirect:/admin/orders/active";
    }
}
