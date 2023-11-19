package com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in;

import com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.requests.CheckoutOrderRequest;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.adapter.in.requests.QuoteItemRequest;
import com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.services.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        Mockito.when(paymentService.pay(Mockito.anyFloat(), Mockito.anyString())).thenReturn(true);
    }

    @Test
    public void testCheckoutOrderCorrectly() throws Exception {
        var checkoutOrderRequest = new CheckoutOrderRequest();
        checkoutOrderRequest.customerId = UUID.randomUUID();
        checkoutOrderRequest.currencyCode = "USD";
        checkoutOrderRequest.items = new QuoteItemRequest[]{
                new QuoteItemRequest(UUID.randomUUID(), 1),
                new QuoteItemRequest(UUID.randomUUID(), 2),
                new QuoteItemRequest(UUID.randomUUID(), 3)
        };

        ResultActions response = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(checkoutOrderRequest)));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.status",
                        is("PAID")));
    }
}