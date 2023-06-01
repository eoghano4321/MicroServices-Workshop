package org.example.service;

import org.example.kafka.UserProducerService;
import org.example.model.User;
import org.example.model.UserMessage;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProducerService userProducerService;

    @Autowired
    public UserService(UserRepository userRepository, UserProducerService userProducerService) {
        this.userRepository = userRepository;
        this.userProducerService = userProducerService;
    }

    public User createUser(User user) {
        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Send the user message to Kafka
        UserMessage userMessage = convertToUserMessage(savedUser);
        userProducerService.sendUserMessage(userMessage);

        return savedUser;
    }

    public User deleteUser(User user){
        user.setUserStatus("cancelled");
        User savedUser = userRepository.save(user);

        // Send the user message to Kafka
        UserMessage userMessage = convertToUserMessage(savedUser);
        userProducerService.sendUserMessage(userMessage);
        return savedUser;
    }

    private UserMessage convertToUserMessage(User user) {
        // Convert User object to UserMessage object
        UserMessage userMessage = new UserMessage();
        userMessage.setId(user.getId());
        userMessage.setUserId(user.getUserId());
//        userMessage.setCustomerId(user.getCustomerId());
//        userMessage.setProductId(user.getProductId());
        userMessage.setQuantity(user.getQuantity());
//        userMessage.setUserDate(user.getUserDate());
        userMessage.setUserStatus(user.getUserStatus());
        userMessage.setUserEmail(user.getUserEmail());

        return userMessage;
    }

}
