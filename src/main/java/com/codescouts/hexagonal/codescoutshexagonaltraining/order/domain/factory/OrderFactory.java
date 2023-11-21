package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.factory;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.CheckoutOrder;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Customer;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Product;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services.PaymentService;
import java.util.List;

public class OrderFactory {

  private final CustomerFactory customerFactory;
  private final ProductFactory productFactory;
  private final PaymentService paymentService;

  public OrderFactory(CustomerFactory customerFactory, ProductFactory productFactory, PaymentService paymentService) {
    this.customerFactory = customerFactory;
    this.productFactory = productFactory;
    this.paymentService = paymentService;
  }

  public Order createOrder(CheckoutOrder checkoutOrder){
    Customer customer = customerFactory.getCustomer(checkoutOrder.customerId());
    List<Product> products = productFactory.createProducts(checkoutOrder.items(), checkoutOrder.currencyCode());
    return new Order(customer,products, checkoutOrder.currencyCode() ,paymentService);
  }

}
