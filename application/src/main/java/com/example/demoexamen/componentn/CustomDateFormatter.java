package com.example.demoexamen.componentn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class CustomDateFormatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/yy");

    public static LocalDate localDateFormatter(String date) {
        return LocalDate.parse(date, DATE_TIME_FORMATTER);
    }
}
