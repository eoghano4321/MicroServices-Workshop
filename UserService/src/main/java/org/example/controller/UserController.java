package org.example.controller;

import jakarta.persistence.criteria.Order;
import org.example.client.OrderServiceClient;
import org.example.model.OrderDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private UserService userService;
    private final OrderServiceClient orderServiceClient;


    @Autowired
    public UserController(UserRepository userRepository, UserService userService, OrderServiceClient orderServiceClient) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        return maybeUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> maybeExistingUser = userRepository.findById(id);
        if (maybeExistingUser.isPresent()) {
            user.setId(id);
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> maybeExistingUser = userRepository.findById(id);

        if (maybeExistingUser.isPresent()) {
            User deletedUser = userService.deleteUser(userRepository.getReferenceById(id));
            return new ResponseEntity<>(deletedUser, HttpStatus.I_AM_A_TEAPOT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/orderHist/{id}")
    public String getMyOrders(@PathVariable Long id){

        return orderServiceClient.getOrderByUser(id).toString();

    }
}
