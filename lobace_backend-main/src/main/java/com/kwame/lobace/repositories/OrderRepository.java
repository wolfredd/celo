package com.kwame.lobace.repositories;

import com.kwame.lobace.Models.Order;
import com.kwame.lobace.Models.Restaurant;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByIdForOrderAndUserId(String idForOrder, Integer userId);

    @Query("SELECT o FROM Order o WHERE o.userId = ?1 ORDER BY o.idForOrder")
    List<Order> findAllByUserIdGroupedByIdForOrder(Integer userId);

}