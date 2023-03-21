package com.whahn.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * feign client decoder
 */
public class CustomDecoder implements Decoder {

    private final Decoder decoder;

    /**
     * zonedatetime deserialize 생성
     * localDateTime deserialize 생성
     */
    public CustomDecoder() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));*/

        simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        simpleModule.addDeserializer(ZonedDateTime.class, new ZoneDateTimeDeserializer());

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
