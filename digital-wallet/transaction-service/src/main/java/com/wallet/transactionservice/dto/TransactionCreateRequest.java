package com.wallet.transactionservice.dto;

import com.wallet.transactionservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {

    private TransactionType type;

    private BigDecimal amount;

    private UUID fromWalletId; // required for WITHDRAW & TRANSFER

    private UUID toWalletId;   // required for DEPOSIT & TRANSFER
}
