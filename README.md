# flexible_blog_search

### 요구사항 검수시 주의사항
- 반드시 인터넷이 되는 환경에서 실행 부탁드립니다 (application, tc포함)
- api 명세서는 크롬으로 오픈 부탁드립니다 (사파리는 한글 깨짐현상 있을수있음)
- 실행방법
  - 어플리케이션 
    - ```
      application: java -jar whahn-blog-api.jar
  - 블로그 검색 API
    - ```
      http://localhost:8080/v1/search/blog?page=1&size=10&sortType=accuracy&corporationType=KAKAO&searchKeyword=카카오뱅크
  - 상위 키워드 검색 API
    - ```
      http://localhost:8080/v1/topten-keyword
- jar donwload link:https://github.com/myroom9/flexible_blog_search/blob/main/whahn-blog-api.jar
- api 명세서 local에서 보는방법: http://localhost:8080/swagger-ui/index.html

### 개발환경
- jdk17, spring boot 3, gradle, H2, JPA

### 오픈소스
- spring cloud 및 openfeign
  - api 통신 라이브러리
- modelmapper
  - dto등 객체 매핑에 유연함을 가져가기 위함
- springdoc-openapi-starter-webmvc-ui
  - api 문서화 및 api 테스트시 활용
- caffeine
  - local cacheManager를 더 좋은 성능을 갖은 manager로 변경하기 위함
- feign-jackson
  - feign client통신시 Json을 활용하기 위함

### 개발 
- ### 패키지 형태
  - 레이어드 아키텍쳐로 가져감
  - 멀티모듈 구현 (공통, api 통신부)
    - settings.gradle에 디렉토리 생성 후 빌드만 하면 기본 디렉토리 생성되게끔 생성

- ### 개발패턴 
  - naver/kakao 블로그 API는 blogService를 interface화 시켜서 활용 
  - Service layer가 fat해짐을 방지하기 위해 중간에 Facade객체 생성
- ### api 
  - 규격서 제공 부분은 swagger 3.0을 활용
  - 기본 틀은 ApiResponse활용
    - 요청시간/응답시간/transactionId/http status/code/data
- ### api 통신 (Feign client)
  - naver / kakao 다른 응답값으로 feign 설정 분리
  - errorDecoder 및 decoder
  - time 형태
    - naver: yyyyMMdd
    - kakao: yyyy-MM-dd'T'HH:mm:ss.SSSXXX
    - 2개를 각기 다른 docoder 생성 후 localDateTime형태로 parse해서 가져오게끔 구현
    - naver는 원천 데이터가 일자까지 제공함으로 시분초는 0으로 진행
  - pagination은 naver / kakao에서 제공 데이터를 활용하여 비슷하게 생성
    - 응답값중 isEnd값은 계산식이 살짝 애매했음
    - kakao는 제공해주는거 바로 사용
    - naver는 제공해주는 값이 없어서 total값을 활용해서 임의구현
- ### 상위 10개 키워드
  - 테이블은 심플하게 keyword / count정도로 생성
  - 상위 키워드 기준
    - 1순위 count, 2순위 update된 시간
  - 대용량 트래픽을 고려한 부분
    - 캐시를 활용함 / ttl은 20초
    - 검색어가 10개 이상인 경우에만 캐시 정책 적용되도록함
  - count정확도를 위한 부분
    - synchronized를 활용
    - local queue JMS를 이용해야 좀 더 안정적으로 구현가능할듯함
- ### 에러핸들링
  - 로직에서 발생하는 에러는 BusinessException 처리
  - 통신에서 발생하는 에러는 FeignClientException 처리
  - 나머지는 global advice에서 처리
- ### kakao 블로그 검색 장애시 naver 블로그 검색 활용
  - try/catch 구문을 이용하여 naver블로그로 요청 될 수 있게끔함
- ### ApiMetaInformation
  - 이 테이블은 각 APIKey들이 소스에 노출되는걸 방지하기 위해서 임의 생성한 테이블
  - default api key를 추가하기 위해 defer-datasource-initialization true 옵션 적용
- ### SortTypeConverter
  - 요청시 accuracy, currency값을 대문자로 매핑하기 위함
- ### code convetion은 checkStyle을 활용
  - git commit시 자동으로 사용하려했으나 멀티모듈환경에서는 오동작하여 수동체크 진행
