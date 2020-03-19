package com.zy.smps_user_service.convert;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConvert implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("");
        return LocalDateTime.parse(s,formatter);
    }

}
