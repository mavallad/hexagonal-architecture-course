package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions;

public class PaymentErrorException extends Throwable {
    public PaymentErrorException() {
        super("Payment error");
    }
}
