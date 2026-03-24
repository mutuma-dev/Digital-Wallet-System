package com.wallet.transactionservice.service;

import com.wallet.transactionservice.constants.ApiPaths;
import com.wallet.transactionservice.constants.ExceptionConstants;
import com.wallet.transactionservice.dto.TransactionCreateRequest;
import com.wallet.transactionservice.dto.TransactionResponse;
import com.wallet.transactionservice.entity.Transaction;
import com.wallet.transactionservice.enums.TransactionType;
import com.wallet.transactionservice.mapper.TransactionMapper;
import com.wallet.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RestTemplate restTemplate;

    // wallet-service URL
    private static final String WALLET_SERVICE_URL = ApiPaths.WALLET_BASE_URL + ApiPaths.API_VERSION_URL + ApiPaths.WALLET_ENDPOINT;

    public TransactionService(TransactionRepository transactionRepository,
                              TransactionMapper transactionMapper,
                              RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public TransactionResponse processTransaction(TransactionCreateRequest request) {

        validateRequest(request);

        switch (request.getType()) {

            case DEPOSIT -> handleDeposit(request);

            case WITHDRAW -> handleWithdraw(request);

            case TRANSFER -> handleTransfer(request);

            default -> throw new RuntimeException(ExceptionConstants.INVALID_TRANSACTION_TYPE);
        }

        // Save transaction AFTER wallet operations succeed
        Transaction transaction = new Transaction(
                request.getType(),
                request.getAmount(),
                request.getFromWalletId(),
                request.getToWalletId()
        );

        Transaction saved = transactionRepository.save(transaction);

        return transactionMapper.toResponse(saved);
    }

    // BUSINESS LOGIC

    private void handleDeposit(TransactionCreateRequest request) {

        if (request.getToWalletId() == null) {
            throw new RuntimeException(ExceptionConstants.DESTINATION_REQUIRED);
        }

        callDepositAPI(request.getToWalletId(), request.getAmount());
    }

    private void handleWithdraw(TransactionCreateRequest request) {

        if (request.getFromWalletId() == null) {
            throw new RuntimeException(ExceptionConstants.SOURCE_REQUIRED);
        }

        callWithdrawAPI(request.getFromWalletId(), request.getAmount());
    }

    private void handleTransfer(TransactionCreateRequest request) {

        if (request.getFromWalletId() == null || request.getToWalletId() == null) {
            throw new RuntimeException(ExceptionConstants.SENDER_RECEIVER_REQUIRED);
        }

        if (request.getFromWalletId().equals(request.getToWalletId())) {
            throw new RuntimeException(ExceptionConstants.CANNOT_TRANSFER_SAME_WALLET);
        }

        // withdraw
        callWithdrawAPI(request.getFromWalletId(), request.getAmount());

        // deposit
        callDepositAPI(request.getToWalletId(), request.getAmount());
    }

    // REST CALLS to wallet service

    private void callDepositAPI(UUID walletId, BigDecimal amount) {

        String url = WALLET_SERVICE_URL + "/" + walletId + "/deposit";

        restTemplate.postForObject(url, amount, Object.class);
    }

    private void callWithdrawAPI(UUID walletId, BigDecimal amount) {

        String url = WALLET_SERVICE_URL + "/" + walletId + "/withdraw";

        restTemplate.postForObject(url, amount, Object.class);
    }

    //  VALIDATION

    private void validateRequest(TransactionCreateRequest request) {

        if (request.getType() == null) {
            throw new RuntimeException(ExceptionConstants.TRANSACTION_TYPE_REQUIRED);
        }

        if (request.getAmount() == null ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(ExceptionConstants.INVALID_AMOUNT);
        }
    }

    //get transaction by id
    public TransactionResponse getTransactionById(UUID transactionId) {

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        return transactionMapper.toResponse(transaction);
    }

    //get transactions
    public List<TransactionResponse> getAllTransactions() {

        return transactionRepository.findAll()
                .stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
