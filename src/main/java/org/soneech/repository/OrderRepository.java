package org.soneech.repository;

import org.soneech.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = ?1")
    List<Order> findAllUserOrders(Long userId);
    @Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.isActive = true")
    List<Order> findActiveUserOrders(Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = ?1 AND o.isActive = false")
    List<Order> findCompletedUserOrders(Long userId);
}
