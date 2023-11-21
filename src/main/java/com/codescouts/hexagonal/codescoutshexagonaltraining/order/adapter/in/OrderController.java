package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.requests.CheckoutOrderRequest;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.responses.CheckoutResponse;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.responses.GetOrderResponse;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.services.OrderRepository;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.usecase.AddOrderUseCase;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.application.usecase.GetOrderUseCase;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands.OrderProductLine;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Customer;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Order;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.entities.Product;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.OrderDoesNotExistException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.PaymentErrorException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.exceptions.StockIsNotEnoughException;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final GetOrderUseCase getOrderUseCase;

    private final AddOrderUseCase addOrderUseCase;


    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    @Autowired
    public OrderController(GetOrderUseCase getOrderUseCase, AddOrderUseCase addOrderUseCase, PaymentService paymentService,
                           OrderRepository orderRepository) {
        this.getOrderUseCase = getOrderUseCase;
        this.addOrderUseCase = addOrderUseCase;
        this.paymentService = paymentService;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderResponse> getOrder(@PathVariable UUID orderId) throws OrderDoesNotExistException {
        var order = this.getOrderUseCase.findOrder(orderId);

        return ResponseEntity.ok(new GetOrderResponse(order.id, order.status));
    }

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutOrderRequest request) throws PaymentErrorException, StockIsNotEnoughException {

        var order = addOrderUseCase.checkoutOrder(request.mapToCheckoutOrder());

        return ResponseEntity.ok(new CheckoutResponse(order.id, order.status));
    }





}
