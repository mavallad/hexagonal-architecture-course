package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.StockIsNotEnoughException;

import java.util.List;
import java.util.UUID;

public class Product {
    public final UUID id;
    public final String name;
    public final float unitPrice;
    public final int quantity;
    public final String currencyCode;
    public int stock;

    public Product(UUID id,
                   String name,
                   float unitPrice,
                   String currencyCode,
                   int quantity,
                   int stock) {
        this.id = id;
        this.name = name;
        this.currencyCode = currencyCode;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.stock = stock;
    }



    public void validateStock() throws StockIsNotEnoughException {
        if (quantity > stock) {
            throw new StockIsNotEnoughException();
        }
    }

    public void updateStock() {
        stock = stock - quantity;
    }
}
