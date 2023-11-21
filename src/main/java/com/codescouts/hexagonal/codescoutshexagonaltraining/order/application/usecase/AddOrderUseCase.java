package com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.usecase;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.services.OrderRepository;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.CheckoutOrder;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.OrderProductLine;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Product;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.PaymentErrorException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.StockIsNotEnoughException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.factory.OrderFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class AddOrderUseCase {

  private final OrderFactory orderFactory;

  private final OrderRepository orderRepository;

  public AddOrderUseCase(OrderFactory orderFactory, OrderRepository orderRepository) {
    this.orderFactory = orderFactory;
    this.orderRepository = orderRepository;
  }

  public Order checkoutOrder(CheckoutOrder checkoutOrder) throws PaymentErrorException, StockIsNotEnoughException {

    var newOrder = orderFactory.createOrder(checkoutOrder);
    newOrder.checkoutOrder();

    this.orderRepository.save(newOrder);

    Thread thread = new Thread(() -> {
      var order = this.orderRepository.findById(newOrder.id);

      try {
        TimeUnit.SECONDS.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      order.status = "DELIVERING";

      this.orderRepository.save(order);
    });

    thread.start();
    return newOrder;
  }
}
