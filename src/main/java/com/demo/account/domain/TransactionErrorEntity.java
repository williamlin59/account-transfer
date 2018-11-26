package com.demo.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Holding inValid transaction data for data persistent.
 */
@Data
@Entity
@Table(name = "TRANSACTION_ERROR")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionErrorEntity implements Serializable {
    private static final long serialVersionUID = -779323858598359832L;
    @Id
    @GenericGenerator(name = "transactionErrorSeq", strategy = "sequence",
            parameters = @Parameter(name = "sequence_name", value = "SEQ_TRANSACTION_ERROR"))
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "transactionErrorSeq")
    @Column(name = "TRANSACTION_ERROR_ID")
    private Long transactionErrorId;
    @Column(name = "SOURCE_ACCOUNT_ID")
    private Long sourceAccountId;
    @Column(name = "TARGET_ACCOUNT_ID")
    private Long targetAccountId;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "TRANSACTION_TIME")
    private LocalDateTime transactionTime;

}
