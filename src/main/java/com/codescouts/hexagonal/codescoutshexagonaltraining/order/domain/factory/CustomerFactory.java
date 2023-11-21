package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.factory;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Customer;
import java.util.UUID;

public class CustomerFactory {

  public Customer getCustomer(UUID id){
    return new Customer(id,
        "John Doe",
        "john.doe@unknown.com",
        "Street 123, springfield",
        "+1 234 567 890");
  }

}
