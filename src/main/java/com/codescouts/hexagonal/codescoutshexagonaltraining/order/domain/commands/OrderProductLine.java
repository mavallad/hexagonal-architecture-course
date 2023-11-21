package com.codescouts.hexagonal.codescoutshexagonaltraining.order.domain.commands;

import java.util.UUID;

public record OrderProductLine(UUID id, int quantity) {
}
