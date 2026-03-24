package com.wallet.transactionservice.mapper;

import com.wallet.transactionservice.dto.TransactionCreateRequest;
import com.wallet.transactionservice.dto.TransactionResponse;
import com.wallet.transactionservice.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    // DTO -> Entity
    public Transaction toEntity(TransactionCreateRequest request) {

        if (request == null) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setTimestamp(LocalDateTime.now());

        return transaction;
    }

    // Entity -> DTO
    public TransactionResponse toResponse(Transaction transaction) {

        if (transaction == null) {
            return null;
        }

        TransactionResponse response = new TransactionResponse();

        response.setTransactionId(transaction.getTransactionId());
        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setFromWalletId(transaction.getFromWalletId());
        response.setToWalletId(transaction.getToWalletId());
        response.setTimestamp(transaction.getTimestamp());

        return response;
    }
}