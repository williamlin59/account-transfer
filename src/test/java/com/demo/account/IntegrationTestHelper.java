package com.demo.account;

public class IntegrationTestHelper {
    public static final String ALL_VALID_JSON = "[\n" +
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
    public static final String SECOND_INVALID_JSON = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :6,\n" +
            "  \"targetAccountId\" :7,\n" +
            "  \"amount\" :40000.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
    public static final String TWO_VALIDE_TRANSACTIONS_ON_SAME_ACCOUNTS = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
    public static final String TWO_INVALID_TRANSACTIONS_ON_SAME_ACCOUNTS = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :10000.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :10000.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
    public static final String ONE_INVALID_TRANSACTION_IN_THE_MIDDLE = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :200.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :4950.00,\n" +
            "   \"transactionTime\":\"2014-03-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
    public static final String TWO_INVALID_TRANSACTIONS_AFTER_ONE_VALID = "[\n" +
            "  {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :4950.00,\n" +
            "    \"transactionTime\":\"2013-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :200.00,\n" +
            "   \"transactionTime\":\"2014-12-18T14:30:40.010\"\n" +
            "\t},\n" +
            "    {\n" +
            "  \"sourceAccountId\" :1,\n" +
            "  \"targetAccountId\" :2,\n" +
            "  \"amount\" :100.00,\n" +
            "   \"transactionTime\":\"2014-03-18T14:30:40.010\"\n" +
            "\t}\n" +
            "  ]";
}
