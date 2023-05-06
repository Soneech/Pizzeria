package org.soneech.service;

import org.soneech.models.*;
import org.soneech.repository.AddressRepository;
import org.soneech.repository.BasketDataRepository;
import org.soneech.repository.OrderDataRepository;
import org.soneech.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDataRepository orderDataRepository;
    private final UserService userService;
    private final BasketDataRepository basketDataRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDataRepository orderDataRepository,
                        UserService userService, BasketDataRepository basketDataRepository,
                        AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderDataRepository = orderDataRepository;
        this.userService = userService;
        this.basketDataRepository = basketDataRepository;
        this.addressRepository = addressRepository;
    }

    public List<Order> getAllUserOrders(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        return orderRepository.findAllUserOrders(user.getId());
    }

    public List<Order> getActiveUserOrders(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        return orderRepository.findActiveUserOrders(user.getId());
    }

    public List<Order> getCompletedUserOrders(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        return orderRepository.findCompletedUserOrders(user.getId());
    }

    @Transactional
    public void addOrder(Address address, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        Address addressFromDB = addressRepository.findDuplicate(
                address.getCity(), address.getStreet(), address.getHouseNumber(), address.getDetails());
        if (addressFromDB != null) {
            address = addressFromDB;
        }
        else {
            address = addressRepository.save(address);
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setIsActive(true);

        order = orderRepository.save(order);

        List<BasketData> basketDataList =
                basketDataRepository.findAllBasketDataForUser(user.getId());

        for (var basketData: basketDataList) {
            basketDataRepository.deleteById(basketData.getId());

            OrderData orderData = new OrderData();
            orderData.setOrder(order);
            orderData.setPizza(basketData.getPizza());
            orderData.setCount(basketData.getCount());
            orderData.setCost(basketData.getCost());

            orderDataRepository.save(orderData);
        }
    }
}
