package com.br.authentication.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "CASHIER", schema = "STORE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Cashier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @Column
    private Integer amount;

    @Column
    private LocalDateTime createdAt;

    @Column
    private String comments;
}
