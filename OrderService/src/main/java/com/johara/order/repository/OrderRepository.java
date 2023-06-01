package com.johara.order.repository;

import com.johara.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT u from Order u where u.customerId =:id")
    List<Order> findByOrderUser(@Param("id") int id);
}
