package com.whahn.feign;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class ZonedDateTimeToLocalDateTimeDeserializerTest {

    @Test
    @DisplayName("[성공] zoneDateTime to LocalDateTime 변환")
    void deserializeSuccessTest() {
        String time = "2023-03-20T01:00:00+09:00";

        LocalDateTime localDate = LocalDateTime.parse(
                time,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        ZonedDateTime zonedDateTime = localDate.atZone(ZoneId.of("Asia/Seoul"));
        ZonedDateTime comparedValue = ZonedDateTime.of(2023, 3, 20, 1, 0, 0, 0, ZoneId.of("Asia/Seoul"));
        Assertions.assertThat(zonedDateTime).isEqualTo(comparedValue);
    }

}