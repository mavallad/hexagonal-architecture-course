package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.out;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.services.OrderRepository;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("JdbcOrderRepository")
public class JdbcOrderRepository implements OrderRepository {
    private static List<Order> orders = new ArrayList<>();

    @Override
    public void save(Order order) {
        //TODO: Save order to database.
        if (order.id != null) {
            return;
        }

        try {
            var fieldId = order.getClass().getDeclaredField("id");
            fieldId.setAccessible(true);
            fieldId.set(order, UUID.randomUUID());

            orders.add(order);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findById(UUID orderId) {
        return orders.stream()
                .filter(order -> order.id.equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
