package com.whahn.feign;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateToLocalDateTimeDeserializerTest {

    @Test
    @DisplayName("[성공] 20230302 String to LocalDateTime")
    void deserializeSuccessTest() throws ParseException {
        String date = "20230302";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date simpleDate = formatter.parse(date);

        LocalDateTime localDateTime = LocalDateTime.ofInstant(simpleDate.toInstant(), ZoneId.of("Asia/Seoul"));
        Assertions.assertThat(localDateTime).isEqualTo(LocalDateTime.of(2023, 3, 2, 0,0,0,0));
    }

}