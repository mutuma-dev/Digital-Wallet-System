package com.wallet.transactionservice.entity;

import com.wallet.transactionservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Setter
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    // can be null
    private UUID fromWalletId;
    private UUID toWalletId;

    @Setter
    @Column(nullable = false)
    private LocalDateTime timestamp;


    public Transaction(TransactionType type, BigDecimal amount,
                       UUID fromWalletId, UUID toWalletId) {
        this.type = type;
        this.amount = amount;
        this.fromWalletId = fromWalletId;
        this.toWalletId = toWalletId;
        this.timestamp = LocalDateTime.now();
    }

}
