package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    private final Date date;
    private final Customer customer;
    public final List<Product> products;
    public String currency;
    public String status;
    public UUID id;

    public Order(Customer customer, List<Product> products, String currency) {
        this.products = products;
        this.currency = currency;
        this.status = "CREATED";
        this.date = new Date();
        this.customer = customer;
    }
}
