package com.wallet.transactionservice.repository;

import com.wallet.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    //implements JPA repository
}
