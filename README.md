# flexible_blog_search

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
- 패키지 형태는 레이어드 아키텍쳐로 가져감
- 멀티모듈 구현 (공통, api 통신부)
- api 규격서 제공 부분은 swagger를 활용
- api 기본 틀은 ApiResponse활용
  - 요청시간/응답시간/transactionId/http status/code/data
- pagination은 naver / kakao에서 제공 데이터를 활용하여 비슷하게 생성
  - 응답값중 isEnd값은 계산식이 살짝 애매했음
  - kakao는 제공해주는거 바로 사용
  - naver는 제공해주는 값이 없어서 total값을 활용해서 임의구현
- naver/kakao 블로그 API는 blogService를 구현해서 활용
  - Service layer가 fat해짐을 방지하기 위해 중간에 Facade객체 생성
- api 통신시 naver / kakao 다른 응답값으로 feign 설정 분리
  - errorDecoder 및 decoder
  - time 형태
    - naver: yyyyMMdd
    - kakao: "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    - 2개를 각기 다른 docoder 생성 후 localDateTime형태로 parse해서 가져오게끔 구현
    - naver는 원천 데이터가 일자까지 제공함으로 시분초는 0으로 진행
- 상위 10개 키워드
  - 테이블은 심플하게 keyword / count정도로 생성
  - 상위 키워드 기준
    - 1순위 count, 2순위 update된 시간
  - 대용량 트래픽을 고려한 부분
    - 캐시를 활용함 / ttl은 1분
  - count정확도를 위한 부분
    - synchronized를 활용
- 에러처리
  - 로직에서 발생하는 에러는 BusinessException 처리
  - 통신에서 발생하는 에러는 FeignClientException 처리
- kakao 블로그 검색 장애시 naver 블로그 검색 활용
  - try/catch 구문을 이용하여 naver블로그로 요청 될 수 있게끔함
- ApiMetaInformation이라는 테이블이 있는데 각 APIKey들이 소스에 노출되는걸 방지하기 위해서 임의 생성한 테이블
- SortTypeConverter
  - 요청시 accuracy, currency값을 대문자로 매핑하기 위함
- code convetion은 checkStyle을 활용
  - git commit시 자동으로 사용하려했으나 멀티모듈환경에서는 오동작하여 수동체크 진행