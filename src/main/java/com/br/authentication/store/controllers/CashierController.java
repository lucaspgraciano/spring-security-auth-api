package com.br.authentication.store.controllers;

import com.br.authentication.store.dtos.CashierDto;
import com.br.authentication.store.services.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cashier")
public class CashierController {

    @Autowired
    private CashierService service;

    @PostMapping
    public ResponseEntity<Void> createCashier(@RequestBody CashierDto dto) {
        service.createCashier(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
