package com.johara.order.controller;

import com.johara.order.model.Order;
import com.johara.order.repository.OrderRepository;
import com.johara.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> maybeOrder = orderRepository.findById(id);
        return maybeOrder.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Optional<Order> maybeExistingOrder = orderRepository.findById(id);
        if (maybeExistingOrder.isPresent()) {
            order.setId(id);
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
        Optional<Order> maybeExistingOrder = orderRepository.findById(id);

        if (maybeExistingOrder.isPresent()) {
            Order deletedOrder = orderService.deleteOrder(orderRepository.getReferenceById(id));
            return new ResponseEntity<>(deletedOrder, HttpStatus.I_AM_A_TEAPOT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @GetMapping("/status/{status}")
    public List<Order> getAllStatus(@PathVariable String status) {
        List<Order> ordersByStatus = orderRepository.findByOrderStatus(status);
        return ordersByStatus;
    }
    @GetMapping("/user/{id}")
    public List<Order>getAllUser(@PathVariable int id){
        List<Order> ordersByUser=orderRepository.findByOrderUser(id);
        return ordersByUser;
    }

}
