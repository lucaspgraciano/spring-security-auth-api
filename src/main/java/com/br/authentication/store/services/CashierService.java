package com.br.authentication.store.services;

import com.br.authentication.store.dtos.CashierDto;
import com.br.authentication.store.entities.Cashier;
import com.br.authentication.store.entities.PaymentType;
import com.br.authentication.store.entities.TransactionType;
import com.br.authentication.store.repositories.CashierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CashierService {

    @Autowired
    private CashierRepository repository;

    public void createCashier(CashierDto dto) {
        Cashier cashier = Cashier.builder()
                .transactionType(TransactionType.valueOf(dto.transactionType()))
                .paymentType(PaymentType.valueOf(dto.paymentType()))
                .value(dto.value())
                .amount(dto.amount())
                .createdAt(LocalDateTime.now())
                .comments(dto.comments())
                .build();

        repository.save(cashier);
    }
}
