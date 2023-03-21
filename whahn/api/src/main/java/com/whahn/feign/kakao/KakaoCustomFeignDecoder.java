package com.whahn.feign.kakao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.whahn.feign.DateToLocalDateTimeDeserializer;
import com.whahn.feign.ZonedDateTimeToLocalDateTimeDeserializer;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * feign client decoder
 */
public class KakaoCustomFeignDecoder implements Decoder {

    private final Decoder decoder;

    /**
     * zonedatetime deserialize 생성
     * localDateTime deserialize 생성
     */
    public KakaoCustomFeignDecoder() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        simpleModule.addDeserializer(LocalDateTime.class, new DateToLocalDateTimeDeserializer());
        simpleModule.addDeserializer(LocalDateTime.class, new ZonedDateTimeToLocalDateTimeDeserializer());

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(simpleModule);

        this.decoder = new JacksonDecoder(objectMapper);
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Object result = decoder.decode(response, type);

        if (result instanceof String) {
            return LocalDateTime.parse((String) result, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        }

        return result;
    }
}
