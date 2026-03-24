package com.wallet.walletservice.service;

import com.wallet.walletservice.constants.ExceptionConstants;
import com.wallet.walletservice.dto.WalletCreateRequest;
import com.wallet.walletservice.dto.WalletResponse;
import com.wallet.walletservice.entity.Wallet;
import com.wallet.walletservice.mapper.WalletMapper;
import com.wallet.walletservice.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    // CREATE WALLET
    public WalletResponse createWallet(WalletCreateRequest request) {

        // Check if user already has a wallet
        walletRepository.findByUserId(request.getUserId()).ifPresent(w -> {
            throw new RuntimeException(ExceptionConstants.WALLET_ALREADY_EXISTS + request.getUserId());
        });

        // Map DTO -> Entity
        Wallet wallet = walletMapper.toWalletEntity(request);

        // Enforce business rule: balance always starts at ZERO
        wallet.setBalance(BigDecimal.ZERO);

        // Save wallet
        Wallet savedWallet = walletRepository.save(wallet);

        // Map Entity -> DTO
        return walletMapper.toWalletResponse(savedWallet);
    }

    // GET WALLET BY ID
    public WalletResponse getWalletById(UUID walletId) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() ->
                        new RuntimeException(ExceptionConstants.WALLET_NOT_FOUND + walletId)
                );

        return walletMapper.toWalletResponse(wallet);
    }

    // GET WALLET BY USER ID
    public WalletResponse getWalletByUserId(UUID userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(ExceptionConstants.WALLET_NOT_FOUND_FOR_USER + userId)
                );

        return walletMapper.toWalletResponse(wallet);
    }

    // GET ALL WALLETS
    public List<WalletResponse> getAllWallets() {

        return walletRepository.findAll()
                .stream()
                .map(walletMapper::toWalletResponse)
                .toList();
    }

    // DEPOSIT
    @Transactional
    public WalletResponse deposit(UUID walletId, BigDecimal amount) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException(ExceptionConstants.WALLET_NOT_FOUND + walletId));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(ExceptionConstants.INVALID_DEPOSIT_AMOUNT);
        }

        wallet.setBalance(wallet.getBalance().add(amount));

        Wallet saved = walletRepository.save(wallet);

        return walletMapper.toWalletResponse(saved);
    }

    //WITHDRAW
    @Transactional
    public WalletResponse withdraw(UUID walletId, BigDecimal amount) {

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new RuntimeException(ExceptionConstants.WALLET_NOT_FOUND));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(ExceptionConstants.INVALID_WITHDRAW_AMOUNT);
        }

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException(ExceptionConstants.INSUFFICIENT_AMOUNT + ExceptionConstants.AVAILABLE_BALANCE + wallet.getBalance());
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        Wallet saved = walletRepository.save(wallet);

        return walletMapper.toWalletResponse(saved);
    }
}