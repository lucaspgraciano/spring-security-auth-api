package com.br.authentication.store.dtos;

import java.math.BigDecimal;

public record CashierDto(
        String transactionType,
        String paymentType,
        BigDecimal value,
        Integer amount,
        String comments
) {}
