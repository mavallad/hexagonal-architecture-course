package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions;

public class StockIsNotEnoughException extends Throwable {
    public StockIsNotEnoughException() {
        super("Stock is not enough");
    }
}
