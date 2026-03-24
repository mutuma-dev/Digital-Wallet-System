package com.wallet.transactionservice.dto;

import com.wallet.transactionservice.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private UUID transactionId;

    private TransactionType type;

    private BigDecimal amount;

    private UUID fromWalletId;

    private UUID toWalletId;

    private LocalDateTime timestamp;
}
