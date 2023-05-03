package org.soneech.service;

import org.soneech.models.BasketData;
import org.soneech.models.Pizza;
import org.soneech.models.User;
import org.soneech.repository.BasketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BasketDataService {
    private final BasketDataRepository basketDataRepository;
    private final UserService userService;
    private final PizzaService pizzaService;

    @Autowired
    public BasketDataService(BasketDataRepository basketDataRepository,
                             UserService userService, PizzaService pizzaService) {
        this.basketDataRepository = basketDataRepository;
        this.userService = userService;
        this.pizzaService = pizzaService;
    }

    public Set<Map.Entry<Pizza, Integer>> getAllBasketDataForUser(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        List<BasketData> basketData = basketDataRepository.findAllBasketDataForUser(user.getId());

        Map<Pizza, Integer> pizzasData = new HashMap<>();
        for (var data: basketData) {
            pizzasData.put(data.getPizza(), data.getCount());
        }

        return pizzasData.entrySet();
    }

    public void saveBasketData(Authentication authentication, Long pizzaId) {
        BasketData basketData = new BasketData();
        User user = userService.getAuthenticatedUser(authentication);
        Pizza pizza = pizzaService.getPizzaById(pizzaId).get();

        basketData.setUser(user);
        basketData.setPizza(pizza);
        basketData.setCount(1);

        basketDataRepository.save(basketData);
    }

    public void increasePizzaCount() {

    }
}
