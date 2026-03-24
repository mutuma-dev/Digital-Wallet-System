package com.wallet.walletservice.repository;

import com.wallet.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalletRepository  extends JpaRepository<Wallet, UUID> {

    //implements JPA repository methods
    Optional<Wallet> findByUserId(UUID userId);
}
