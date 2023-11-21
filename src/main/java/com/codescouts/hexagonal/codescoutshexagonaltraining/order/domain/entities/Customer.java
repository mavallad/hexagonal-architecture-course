package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities;

import java.util.UUID;

public class Customer {
    public final UUID id;
    public final String name;
    public final String email;
    public final String address;
    public final String phone;

    public Customer(UUID id, String name, String email, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
}
