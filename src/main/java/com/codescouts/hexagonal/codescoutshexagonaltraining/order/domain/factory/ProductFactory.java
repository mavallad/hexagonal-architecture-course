package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.factory;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.OrderProductLine;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Product;
import java.util.List;

public class ProductFactory {

    public List<Product> createProducts(List<OrderProductLine> items, String currency) {
      return items.stream().map(line -> new Product(line.id(), "Product " + line.id(), 20, currency, line.quantity(), 100)).toList();

    }
}
