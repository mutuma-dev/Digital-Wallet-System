package com.wallet.walletservice.mapper;

import com.wallet.walletservice.dto.WalletCreateRequest;
import com.wallet.walletservice.dto.WalletResponse;
import com.wallet.walletservice.entity.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WalletMapper {

    // DTO -> Entity
    public Wallet toWalletEntity(WalletCreateRequest request) {

        if (request == null) {
            return null;
        }

        Wallet wallet = new Wallet();

        wallet.setUserId(request.getUserId());


        // Balance validation
        if (request.getBalance() != null) {
            wallet.setBalance(request.getBalance());
        } else {
            wallet.setBalance(BigDecimal.ZERO);
        }

        return wallet;
    }

    // Entity -> DTO
    public WalletResponse toWalletResponse(Wallet wallet) {

        if (wallet == null) {
            return null;
        }

        WalletResponse response = new WalletResponse();

        response.setWalletId(wallet.getWalletId());
        response.setUserId(wallet.getUserId());
        response.setBalance(wallet.getBalance());

        return response;
    }
}