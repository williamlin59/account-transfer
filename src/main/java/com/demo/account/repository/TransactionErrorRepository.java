package com.demo.account.repository;

import com.demo.account.domain.TransactionErrorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for TransactionError Table
 */
@Repository
public interface TransactionErrorRepository extends JpaRepository<TransactionErrorEntity, Long> {
}
