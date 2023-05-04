package org.soneech.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.soneech.models.Pizza;
import org.soneech.repository.PizzaRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PizzaServiceTest {
    @Mock
    private PizzaRepository pizzaRepository;

    @Test
    public void getAllPizzas() {
        Pizza pizza = new Pizza();
        pizza.setName("Ветчина и сыр");
        Pizza pizza1 = new Pizza();
        pizza1.setName("Мясная");

        Mockito.when(pizzaRepository.findAll()).thenReturn(List.of(pizza, pizza1));
        PizzaService pizzaService = new PizzaService(pizzaRepository);

        Assertions.assertEquals(2, pizzaService.getAllPizzas().size());
        Assertions.assertEquals("Мясная", pizzaService.getAllPizzas().get(1).getName());
    }

    @Test
    public void getPizzaById() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        pizza.setName("Мясная");

        Mockito.when(pizzaRepository.getById(1L)).thenReturn(pizza);
        PizzaService pizzaService = new PizzaService(pizzaRepository);

        Assertions.assertEquals(pizzaService.getPizzaById(1L).getName(), "Мясная");
    }
}
