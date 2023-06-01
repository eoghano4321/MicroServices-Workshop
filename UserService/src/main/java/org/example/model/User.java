package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int userId;

    //private Long customerId;

    //private Long productId;

    private int quantity;

    //private LocalDateTime userDate;

    private String userEmail;

    private String userStatus;

    // Constructors

    public User() {
    }

    public User(int userId, int quantity, String userEmail, String userStatus) {
        this.userId = userId;
        //this.customerId = customerId;
        //this.productId = productId;
        this.quantity = quantity;
        //this.userDate = userDate;
        this.userEmail = userEmail;
        this.userStatus = userStatus;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

//    public Long getCustomerId() {
//        return customerId;
//    }

//    public void setCustomerId(Long customerId) {
//        this.customerId = customerId;
//    }

//    public Long getProductId() {
//        return productId;
//    }

//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public String getUserEmail(){
        return this.userEmail;
    }

    public void setUserStatus(String userStatus){
        this.userStatus = userStatus;
    }

    public String getUserStatus(){
        return this.userStatus;
    }

//    public LocalDateTime getUserDate() {
//        return userDate;
//    }
//
//    public void setUserDate(LocalDateTime userDate) {
//        this.userDate = userDate;
//    }
}
