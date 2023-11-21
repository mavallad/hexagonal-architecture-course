package com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.usecase;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.OrderDoesNotExistException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetOrderUseCase {

  public Order findOrder(UUID orderId) throws OrderDoesNotExistException {

    Order order = null;

    if (order == null) {
      throw new OrderDoesNotExistException(orderId);
    }
    return order;
  }
}