package com.demo.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TransactionEntity holding data from TransactionResource for applying business logic
 */
@Data
@AllArgsConstructor
public class TransactionEntity implements Serializable, Comparable {
    private static final long serialVersionUID = 7645455016474925292L;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionTime;

    @Override
    public int compareTo(Object o) {
        return this.transactionTime.compareTo(((TransactionEntity) o).getTransactionTime());
    }
}
