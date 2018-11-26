package com.demo.account.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Util for getting current time UTC Time Zone
 */
public class DateTimeUtil {
    private static final String UTC = "UTC";

    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(UTC));
    }
}
