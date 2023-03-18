package com.whahn.controller.dto;

import com.whahn.common.ModelMapperUtil;
import com.whahn.feign.dto.KakaoBlogContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogSearchPagingResponse {

    private Meta meta;
    private List<Document> document;

    @Data
    @Builder
    private static class Meta {
        private int currentPage;
        private int totalContentCount;
        private int size;
        private boolean isEnd;
    }

    @Data
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;

        // thumnail이 존재하지 않으면 default 값 넣어서 전달
        private String thumbnail;
        private String datetime;
    }

    public static BlogSearchPagingResponse kakaoBlogResultToEntity(CustomRequestPaging request, KakaoBlogContent contents) {
        Meta meta = Meta.builder()
                .size(request.getSize())
                .currentPage(request.getPage())
                .totalContentCount(contents.getMeta().getPageableCount())
                .isEnd(false)
                .build();

        List<Document> documents = contents.getDocuments().stream()
                .map(o -> ModelMapperUtil.map(o, Document.class))
                .toList();

        return BlogSearchPagingResponse
                .builder()
                .meta(meta)
                .document(documents)
                .build();
    }

}
