package com.demo.account.integration;

import com.demo.account.IntegrationTestHelper;
import com.demo.account.controller.TransactionController;
import com.demo.account.domain.TransactionErrorEntity;
import com.demo.account.repository.TransactionErrorRepository;
import com.demo.account.service.LocalCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
public class IntegrationTest {
    private MockMvc mockMvc;
    @Autowired
    private TransactionController transactionController;

    @Autowired
    private LocalCacheService localCacheService;

    @Autowired
    private TransactionErrorRepository transactionErrorRepository;

    @BeforeEach
    void setUp() {
        transactionErrorRepository.deleteAllInBatch();
        localCacheService.initializeLocalCacheForTest();
        mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    void testAllValidTransactions() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(IntegrationTestHelper.ALL_VALID_JSON))
                .andExpect(status().isAccepted());
        Thread.sleep(1000);
        assertThat(localCacheService.getAmount(1L).intValueExact()).isEqualTo(new BigDecimal(4900).intValueExact());
        assertThat(localCacheService.getAmount(2L).intValueExact()).isEqualTo(new BigDecimal(5100).intValueExact());
        assertThat(localCacheService.getAmount(6L).intValueExact()).isEqualTo(new BigDecimal(4600).intValueExact());
        assertThat(localCacheService.getAmount(7L).intValueExact()).isEqualTo(new BigDecimal(5400).intValueExact());
    }

    @Test
    void testValidAndInvalidTransactions() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(IntegrationTestHelper.SECOND_INVALID_JSON))
                .andExpect(status().isAccepted());
        Thread.sleep(1000);
        assertThat(localCacheService.getAmount(1L).intValueExact()).isEqualTo(new BigDecimal(4900).intValueExact());
        assertThat(localCacheService.getAmount(2L).intValueExact()).isEqualTo(new BigDecimal(5100).intValueExact());
        assertThat(localCacheService.getAmount(6L).intValueExact()).isEqualTo(new BigDecimal(5000).intValueExact());
        assertThat(localCacheService.getAmount(7L).intValueExact()).isEqualTo(new BigDecimal(5000).intValueExact());
        List<TransactionErrorEntity> transactionErrorEntities = transactionErrorRepository.findAll();
        assertThat(transactionErrorEntities.size()).isEqualTo(1);
        verifyTransactionErrorEntity(transactionErrorEntities.get(0), 6L, 7L, new BigDecimal(40000));
    }

    @Test
    void testTwoValidTransactionsOnSameAccounts() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(IntegrationTestHelper.TWO_VALIDE_TRANSACTIONS_ON_SAME_ACCOUNTS))
                .andExpect(status().isAccepted());
        Thread.sleep(1000);
        assertThat(localCacheService.getAmount(1L).intValueExact()).isEqualTo(new BigDecimal(4800).intValueExact());
        assertThat(localCacheService.getAmount(2L).intValueExact()).isEqualTo(new BigDecimal(5200).intValueExact());
    }

    @Test
    void testOneInvalidTransactionsOnSameAccountsInTheMiddle() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(IntegrationTestHelper.ONE_INVALID_TRANSACTION_IN_THE_MIDDLE))
                .andExpect(status().isAccepted());
        Thread.sleep(1000);
        assertThat(localCacheService.getAmount(1L).intValueExact()).isEqualTo(new BigDecimal(4700).intValueExact());
        assertThat(localCacheService.getAmount(2L).intValueExact()).isEqualTo(new BigDecimal(5300).intValueExact());
        List<TransactionErrorEntity> transactionErrorEntities = transactionErrorRepository.findAll();
        assertThat(transactionErrorEntities.size()).isEqualTo(1);
        verifyTransactionErrorEntity(transactionErrorEntities.get(0), 1L, 2L, new BigDecimal(4950));
    }

    @Test
    void testTwoInvalidTransactionsOnSameAccountsAtTheEnd() throws Exception {
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(IntegrationTestHelper.TWO_INVALID_TRANSACTIONS_AFTER_ONE_VALID))
                .andExpect(status().isAccepted());
        Thread.sleep(1000);
        assertThat(localCacheService.getAmount(1L).intValueExact()).isEqualTo(new BigDecimal(50).intValueExact());
        assertThat(localCacheService.getAmount(2L).intValueExact()).isEqualTo(new BigDecimal(9950).intValueExact());
        List<TransactionErrorEntity> transactionErrorEntities = transactionErrorRepository.findAll();
        System.out.println(transactionErrorEntities);
        assertThat(transactionErrorEntities.size()).isEqualTo(2);
        verifyTransactionErrorEntity(transactionErrorEntities.get(0), 1L, 2L, new BigDecimal(100));
        verifyTransactionErrorEntity(transactionErrorEntities.get(1), 1L, 2L, new BigDecimal(200));
    }

    private void verifyTransactionErrorEntity(TransactionErrorEntity transactionErrorEntity, Long expectedSourceAccountId, Long expectedTargetAccountId, BigDecimal expectedAmount) {
        assertThat(transactionErrorEntity.getTargetAccountId()).isEqualTo(expectedTargetAccountId);
        assertThat(transactionErrorEntity.getSourceAccountId()).isEqualTo(expectedSourceAccountId);
        assertThat(transactionErrorEntity.getAmount().intValueExact()).isEqualTo(expectedAmount.intValueExact());
    }
}
