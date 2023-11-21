package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services;

public interface PaymentService {
    boolean pay(float money, String currency);
}

