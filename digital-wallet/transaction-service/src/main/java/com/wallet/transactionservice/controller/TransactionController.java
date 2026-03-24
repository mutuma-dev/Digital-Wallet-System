package com.wallet.transactionservice.controller;

import com.wallet.transactionservice.constants.ApiPaths;
import com.wallet.transactionservice.dto.TransactionCreateRequest;
import com.wallet.transactionservice.dto.TransactionResponse;
import com.wallet.transactionservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.API_VERSION_URL + ApiPaths.TRANSACTION_BASE_URL)
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody TransactionCreateRequest request) {

        TransactionResponse response = transactionService.processTransaction(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {

        TransactionResponse response = transactionService.getTransactionById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {

        List<TransactionResponse> transactions = transactionService.getAllTransactions();

        return ResponseEntity.ok(transactions);
    }
}
