package com.whahn.controller.dto;

import com.whahn.common.ModelMapperUtil;
import com.whahn.entity.KeywordCount;
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
    private List<TopTenKeyword> topTenKeywords;

    @Data
    @Builder
    public static class Meta {
        private int currentPage;
        private int totalContentCount;
        private int size;
        private boolean isEnd;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {
        private String title;
        private String contents;
        private String url;
        private String blogname;

        // thumnail이 존재하지 않으면 default 값 넣어서 전달
        private String thumbnail;
        private String datetime;
    }

    @Data
    public static class TopTenKeyword {
        private String keyword;
        private long count;

        public TopTenKeyword(KeywordCount keywordCount) {
            this.keyword = keywordCount.getKeyword();
            this.count = keywordCount.getCount();
        }
    }

    /**
     * 블로그 응답 객체 + 탑텐 키워드 매핑하는 함수
     */
    public static BlogSearchPagingResponse addTopTenKeywordsToBlogResponse(BlogSearchPagingResponse blogResponse, List<KeywordCount> keywordCounts) {
        List<TopTenKeyword> topTenKeywordsResponse = keywordCounts.stream().map(TopTenKeyword::new).toList();
        blogResponse.setTopTenKeywords(topTenKeywordsResponse);
        return blogResponse;
    }

    /**
     * 카카오 블로그 응답 객체를 엔드유저한테 응답하는 객체로 매핑
     */
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
