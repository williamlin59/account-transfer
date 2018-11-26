package com.demo.account.repository;

import com.demo.account.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * JPA Repository for Account Table
 */
@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Modifying(clearAutomatically = true)
    @Query(value = "update Account account set account.amount =:amount where account.account_id =:accountId", nativeQuery = true)
    void updateAmountForAccountId(@Param("amount") BigDecimal amount, @Param("accountId") Long accountId);
}
