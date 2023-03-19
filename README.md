# flexible_blog_search

### 레이어드 아키텍쳐

### tc -> exception -> feign 손보기 -> 큐?

### 정리
- feign client time / errordecoder
  - tc도 필요함
- 네이버 api 연동
- 키워드 저장 실패시 무시할 수 있도록
- api 문서
  - swagger json 으로 처리 가능함
- 멀티 모듈사용
- 캐시사용
  - 대용량 트래픽관점
- 동시성은 synchronized
- advice쪽도 소스정리 필요할것임