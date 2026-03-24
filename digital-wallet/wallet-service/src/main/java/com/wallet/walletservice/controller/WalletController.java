package com.wallet.walletservice.controller;

import com.wallet.walletservice.constants.ApiPaths;
import com.wallet.walletservice.dto.WalletCreateRequest;
import com.wallet.walletservice.dto.WalletResponse;
import com.wallet.walletservice.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiPaths.API_VERSION_URL + ApiPaths.WALLET_BASE_URL)
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // CREATE WALLET
    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(@RequestBody WalletCreateRequest request) {

        WalletResponse response = walletService.createWallet(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET WALLET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getWalletById(@PathVariable UUID id) {

        WalletResponse response = walletService.getWalletById(id);

        return ResponseEntity.ok(response);
    }

    // GET WALLET BY USER ID
    @GetMapping("/users/{userId}")
    public ResponseEntity<WalletResponse> getWalletByUserId(@PathVariable UUID userId) {

        WalletResponse response = walletService.getWalletByUserId(userId);

        return ResponseEntity.ok(response);
    }

    // GET ALL WALLETS
    @GetMapping
    public ResponseEntity<List<WalletResponse>> getAllWallets() {

        List<WalletResponse> wallets = walletService.getAllWallets();

        return ResponseEntity.ok(wallets);
    }

    // Deposit endpoint
    @PostMapping("/{id}/deposit")
    public ResponseEntity<WalletResponse> deposit(
            @PathVariable UUID walletId,
            @RequestBody BigDecimal amount) {

        WalletResponse response = walletService.deposit(walletId, amount);

        return ResponseEntity.ok(response);
    }

    //withdraw endpoint
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<WalletResponse> withdraw(
            @PathVariable UUID walletId,
            @RequestBody BigDecimal amount) {

        WalletResponse response = walletService.withdraw(walletId, amount);

        return ResponseEntity.ok(response);
    }
}
