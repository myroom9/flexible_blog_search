package com.whahn.controller.dto;

import com.whahn.common.ModelMapperUtil;
import com.whahn.entity.KeywordCount;
import com.whahn.feign.dto.KakaoBlogContent;
import com.whahn.feign.dto.NaverBlogContent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "블로그/인기검색어 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogSearchPagingResponse {

    @Schema(description = "블로그/인기검색어 응답 META 정보")
    private Meta meta;
    @Schema(description = "블로그 응답값")
    private List<Document> document;
    @Schema(description = "인기 검색어 탑 10 !")
    private List<TopTenKeyword> topTenKeywords;

    @Data
    @Builder
    public static class Meta {
        @Schema(description = "현재 페이지")
        private int currentPage;
        @Schema(description = "컨텐츠 총 개수")
        private int totalContentCount;
        @Schema(description = "페이징 사이즈")
        private int size;
        @Schema(description = "마지막 페이지 여부")
        private boolean isEnd;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Document {
        @Schema(description = "블로그 글제목")
        private String title;
        @Schema(description = "블로그 내용")
        private String contents;
        @Schema(description = "블로그 URL")
        private String url;
        @Schema(description = "블로그명")
        private String blogname;

        @Schema(description = "블로그 썸네일")
        private String thumbnail;
        @Schema(description = "블로그 글 등록시간")
        private String datetime;

        public Document(NaverBlogContent.Document document) {
            this.setTitle(document.getTitle());
            this.setBlogname(document.getBloggername());
            this.setUrl(document.getLink());
            this.setContents(document.getDescription());
            this.setDatetime(document.getPostdate());
        }
    }

    @Data
    public static class TopTenKeyword {
        @Schema(description = "인기 검색어")
        private String keyword;
        @Schema(description = "인기 검색어 검색 횟수")
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
                .isEnd(contents.getMeta().isEnd())
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

    /**
     * 네이버 블로그 응답 객체를 엔드유저한테 응답하는 객체로 매핑
     */
    public static BlogSearchPagingResponse naverBlogResultToEntity(CustomRequestPaging request, NaverBlogContent contents) {
        int totalContentCount = contents.getTotal();
        int size = request.getSize();
        int page = request.getPage();
        boolean isEnd = totalContentCount == 0 || totalContentCount <= (size * page);

        Meta meta = Meta.builder()
                .size(size)
                .currentPage(page)
                .totalContentCount(totalContentCount)
                .isEnd(isEnd)
                .build();

        List<Document> documents = contents.getItems().stream()
                .map(Document::new)
                .toList();

        return BlogSearchPagingResponse
                .builder()
                .meta(meta)
                .document(documents)
                .build();
    }

}
