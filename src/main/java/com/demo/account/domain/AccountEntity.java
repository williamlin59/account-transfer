package com.demo.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity holding account data for back persist
 */
@Data
@Entity
@Table(name = "ACCOUNT")
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity implements Serializable {
    private static final long serialVersionUID = 6686859898251428417L;
    @Id
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CREATE_TIME", insertable = false)
    private transient LocalDateTime createTime;

}
