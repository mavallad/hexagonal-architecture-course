package com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.usecase;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.services.OrderRepository;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.OrderDoesNotExistException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetOrderUseCase {

  private final OrderRepository orderRepository;

  public GetOrderUseCase(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Order findOrder(UUID orderId) throws OrderDoesNotExistException {

    Order order = orderRepository.findById(orderId);

    if (order == null) {
      throw new OrderDoesNotExistException(orderId);
    }
    return order;
  }
}