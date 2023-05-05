package org.soneech.repository;

import org.soneech.models.BasketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketDataRepository extends JpaRepository<BasketData, Long> {
    @Query("SELECT b FROM BasketData b WHERE b.user.id = ?1")
    List<BasketData> findAllBasketDataForUser(Long userId);

    @Query("SELECT b FROM BasketData b WHERE b.user.id = ?1 AND b.pizza.id = ?2")
    BasketData findCertainBasketDataForUser(Long userId, Long pizzaId);
}
