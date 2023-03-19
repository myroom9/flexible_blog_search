package com.whahn.config;

import com.whahn.type.blog.SortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * SortType 요청값 매핑시 소문자도 대문자로 변경하여 enum과 매핑하기 위함
 */
@Component
public class SortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String source) {
        return SortType.valueOf(source.toUpperCase());
    }
}
