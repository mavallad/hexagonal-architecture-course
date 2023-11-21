package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.PaymentErrorException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.StockIsNotEnoughException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services.PaymentService;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;

public class Order {
    private final Date date;
    private final Customer customer;
    public final List<Product> products;
    public String currency;
    public String status;
    public UUID id;


    private final PaymentService paymentService;

    public Order(Customer customer, List<Product> products, String currency, PaymentService paymentService) {
        this.products = products;
        this.currency = currency;
        this.status = "CREATED";
        this.date = new Date();
        this.customer = customer;
        this.paymentService = paymentService;
    }

    public Order checkoutOrder() throws PaymentErrorException, StockIsNotEnoughException {
        validateStock();
        pay();
        updateStock();
        return this;
    }

    private void validateStock() throws StockIsNotEnoughException  {
        for (Product product : products ) {
            product.validateStock();
        }

    }
    private void pay() throws PaymentErrorException {
        var payment = paymentService.pay(getTotal(), currency);

        if (!payment) {
            throw new PaymentErrorException();
        }
        status = "PAID";
    }

    private void updateStock(){
        for (Product product : products ) {
            product.updateStock();
        }
    }

    private float getTotal() {
        return products.stream()
            .map((product) -> product.unitPrice * product.quantity)
            .reduce(0F, Float::sum);
    }
}
