package com.whahn.feign.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 네이버 블로그 검색 API 응답
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogContent {
    private int total;
    private int start;
    private int display;
    private List<Document> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloggerlink;

        @JsonFormat(pattern = "yyyyMMdd", shape = JsonFormat.Shape.STRING)
        private LocalDateTime postdate;
    }
}
