package org.soneech.service;

import org.soneech.models.BasketData;
import org.soneech.models.Pizza;
import org.soneech.models.User;
import org.soneech.repository.BasketDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public BasketData getBasketDataForId(Long id) {
        return basketDataRepository.getById(id);
    }

    public List<BasketData> getAllBasketDataForUser(Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        List<BasketData> basketDataList = basketDataRepository.findAllBasketDataForUser(user.getId());

        basketDataList.sort(Comparator.comparing(o -> o.getPizza().getName()));

        return basketDataList;
    }

    public int getTotalCost(List<BasketData> basketDataList) {
        int sum = 0;
        for (var data: basketDataList) {
            sum += data.getCost();
        }
        return sum;
    }

    public BasketData getCertainBasketDataForUser(Long userId, Long pizzaId) {
        return basketDataRepository.findCertainBasketDataForUser(userId, pizzaId);
    }

    @Transactional
    public void saveBasketData(Authentication authentication, Long pizzaId) {
        User user = userService.getAuthenticatedUser(authentication);
        Pizza pizza = pizzaService.getPizzaById(pizzaId);

        BasketData basketData = getCertainBasketDataForUser(user.getId(), pizzaId);

        if (basketData != null) {
            increaseBasketDataCount(basketData);
            increaseCost(basketData);
            basketDataRepository.save(basketData);
        }

        else {
            basketData = new BasketData();
            basketData.setUser(user);
            basketData.setPizza(pizza);
            basketData.setCount(1);
            basketData.setCost(pizza.getPrice());
            basketDataRepository.save(basketData);
        }
    }

    public void increasePizzaInBasket(Long basketDataId) {
        BasketData basketData = getBasketDataForId(basketDataId);

        increaseBasketDataCount(basketData);
        increaseCost(basketData);

        basketDataRepository.save(basketData);
    }

    public void decreasePizzaInBasket(Long basketDataId) {
        BasketData basketData = getBasketDataForId(basketDataId);
        if (basketData.getCount() > 1) {
            decreaseBasketDataCount(basketData);
            decreaseCost(basketData);

            basketDataRepository.save(basketData);
        }
    }

    public void increaseBasketDataCount(BasketData basketData) {
        basketData.setCount(basketData.getCount() + 1);
    }

    public void increaseCost(BasketData basketData) {
        basketData.setCost(basketData.getCost() + basketData.getPizza().getPrice());
    }

    public void decreaseBasketDataCount(BasketData basketData) {
        basketData.setCount(basketData.getCount() - 1);
    }

    public void decreaseCost(BasketData basketData) {
        basketData.setCost(basketData.getCost() - basketData.getPizza().getPrice());
    }

    public void deletePizzaFromBasket(Long id) {
        basketDataRepository.deleteById(id);
    }
}
