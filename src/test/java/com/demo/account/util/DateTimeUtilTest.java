package com.demo.account.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeUtilTest {

    @Test
    void getCurrentLocalDateTime() {
        assertThat(DateTimeUtil.getCurrentLocalDateTime()).isNotNull();
    }

}