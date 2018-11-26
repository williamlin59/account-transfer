package com.demo.account.controller;

import com.demo.account.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class TransactionControllerTest {
    private static final String POST_BODY_JSON = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :6,\n" +
            "  \"targetAccountId\" :7,\n" +
            "  \"amount\" :400.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
    private MockMvc mockMvc;
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionServiceMock;

    @BeforeEach
    void setUp() {
        initMocks(this);
        transactionController = new TransactionController(transactionServiceMock);
        mockMvc = standaloneSetup(transactionController).build();
    }

    @Test
    void multipleTransactions() throws Exception {
        doNothing().when(transactionServiceMock).solveTransactions(anyList());
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON_VALUE).content(POST_BODY_JSON))
                .andExpect(status().isAccepted());
        verify(transactionServiceMock).solveTransactions(anyList());
    }
}