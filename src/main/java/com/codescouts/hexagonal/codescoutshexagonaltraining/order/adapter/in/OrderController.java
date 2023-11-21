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
        var user = this.GetUser(request.customer());
        var products = this.GetProducts(request.items(), request.currencyCode);

        var newOrder = new Order(user, products, request.currencyCode);

        this.validateStock(newOrder);

        this.pay(newOrder);

        this.updateStock(newOrder);

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

        return ResponseEntity.ok(new CheckoutResponse(newOrder.id, newOrder.status));
    }

    private Customer GetUser(UUID customerId) {
        // TODO: Get customer by id from database and/or external service.
        return new Customer(customerId,
                "John Doe",
                "john.doe@unknown.com",
                "Street 123, springfield",
                "+1 234 567 890");
    }

    private List<Product> GetProducts(List<OrderProductLine> items, String currency) {
        //TODO: the currency is used to convert the price of the product.
        //TODO: find products in database.

        return items.stream()
                .map(line ->
                        new Product(line.id(),
                                "Product " + line.id(),
                                20,
                                currency,
                                line.quantity(),
                                100)
                )
                .toList();
    }

    private void validateStock(Order order) throws StockIsNotEnoughException {
        if (order.products.stream().anyMatch(product -> product.quantity > product.stock)) {
            throw new StockIsNotEnoughException();
        }
    }

    private void pay(Order order) throws PaymentErrorException {
        var payment = this.paymentService.pay(this.getTotal(order), order.currency);

        if (!payment) {
            throw new PaymentErrorException();
        }

        order.status = "PAID";
    }

    private float getTotal(Order order) {
        return order.products.stream()
                .map((product) -> product.unitPrice * product.quantity)
                .reduce(0F, Float::sum);
    }

    private void updateStock(Order order) {
        order.products.forEach(product -> product.stock = product.stock - product.quantity);
    }
}