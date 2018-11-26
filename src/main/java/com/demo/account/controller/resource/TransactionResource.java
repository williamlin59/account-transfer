package com.demo.account.controller.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Resource hold data from input json
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResource implements Serializable {
    private static final long serialVersionUID = 6890667957230532290L;
    private Long sourceAccountId;
    private Long targetAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionTime;
}
