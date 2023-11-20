package com.br.authentication.store.repositories;

import com.br.authentication.store.entities.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierRepository extends JpaRepository<Cashier, Long> {
}
