# 갓잇 GOT IT
### 필요한 상품을 찾으세요.
### 카테고리별, 브랜드별 특정 조건의 상품을 검색 가능한 토이 프로젝트

- 주요 라이브러리
    - openjdk 21
    - ```
      brew install openjdk@21
      ```
    - spring boot 3.4.0
    - kotlin 1.9.25

- 빌드 방법
    - 프로젝트 루트에서 명령어를 입력합니다.  
      `./gradlew clean build`
    - 테스트가 전부 통과하면 프로젝트 루트에서 `build/libs` 에서 생성된 jar 파일을 확인합니다.

- 구동 방법
    - **반드시 `./gradlew clean build` 을 실행하여 그레들 빌드하여 jar파일을 생성합니다.**
    -  터미널에서 프로젝트 루트에서 아래 명령어를 입력합니다.
    - `java -jar build/libs/got-it-0.0.1-SNAPSHOT.jar`

- 샘플 프론트엔드 접근 방법
    - 프로젝트를 구동합니다. 
    - http://localhost:8080 을 브라우저에 입력하여 인덱스 페이지로 이동합니다.
    - 샘플 페이지 네비게이션을 통해 각 기능으로 이동합니다.

- RDB 접근 방법
    - 서버 구동후 아래 URL을 브라우저에 입력합니다.
    - `http://localhost:8080/h2-console`
    - 아래 정보를 입력하여 RDB 접근 가능합니다.
        - JDBC URL : **jdbc:h2:mem:testdb;MODE=MySQL**
        - 아이디 **sa** / 비번 **없음**
    - DB 스키마 경로
    - `resources/schema.sql`

- 구현 아이디어 및 API 명세
- **서버 호스트**: `http://localhost:8080`

1. 카테고리별 최저가격인 브랜드와 가격을 조회하는 API
  - 구현 아이디어
     - 셀프 조인으로 같은 카테고리 내에서 자신보다 높은 상품이 없는지를 쿼리
     - 가격이 같은 상품은 최신순의 pk를 가진 상품을 쿼리

  - API 명세
    - Http Method: `GET`
    - 엔드포인트: `/v1/finder/categories/products/min-price`
    -  ### Request Param
       - **없음**
    - ### Response body(예제)
      - <details>
        <summary>접기/펼치기</summary>
        
        ```json
        {
        "상품": [
          {
              "카테고리": "상의",
              "브랜드": "C",
              "가격": 10000
          },
          {
              "카테고리": "아우터",
              "브랜드": "E",
              "가격": 5000
          },
          {
              "카테고리": "바지",
              "브랜드": "D",
              "가격": 3000
          },
          {
              "카테고리": "스니커즈",
              "브랜드": "G",
              "가격": 9000
          },
          {
              "카테고리": "가방",
              "브랜드": "A",
              "가격": 2000
          },
          {
              "카테고리": "모자",
              "브랜드": "D",
              "가격": 1500
          },
          {
              "카테고리": "양말",
              "브랜드": "I",
              "가격": 1700
          },
          {
              "카테고리": "액세서리",
              "브랜드": "F",
              "가격": 1900
          }
        ],
        "총액": 34100
        }
        ```
       </details>

2. 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저 가격인 브랜드를 조회하는 API
  - 구현 아이디어
       - 브랜드의 카테고리 별로 최소 가격을 어그리게이션한 총 금액을 기준으로 정렬한 브랜드를 우선 쿼리 
       - 가장 최소 총액을 가지는 브랜드를 픽하여 셀프 조인 아이디어를 활용하여 카테고리별 최소 금액 상품 쿼리
       - 복잡한 서브쿼리를 분리
  - API 명세
       - Http Method: `GET`
       - 엔드포인트: `/v1/finder/brands/products/min-price`
       -  ### Request Param
          - 없음
       - ### Response body(예제)
          - <details>
              <summary>접기/펼치기</summary>
             
              ```json
               {
                  "최저가": {
                    "브랜드": "D",
                    "카테고리": [
                      {
                        "카테고리": "상의",
                        "가격": 10100
                      },
                      {
                        "카테고리": "아우터",
                        "가격": 5100
                      },
                      {
                         "카테고리": "바지",
                         "가격": 3000
                      },
                      {
                         "카테고리": "스니커즈",
                         "가격": 9500
                      },
                      {
                          "카테고리": "가방",
                          "가격": 2500
                      },
                      {
                          "카테고리": "모자",
                          "가격": 1500
                      },
                      {
                          "카테고리": "양말",
                          "가격": 2400
                      },
                      {
                          "카테고리": "액세서리",
                          "가격": 2000
                      }
                    ],
                    "총액": 36100
                  }
               }
              ```
            </details>

3. 특정 카테고리에서 최저가격, 최고가격 브랜드를 조회하는 API
  - 구현 아이디어
     - 특정 카테고리를 기준으로 최소금액을 우선 조회
     - 최소 금액과 일치하는 상품 리스트를 조회
     - 최대 금액도 동일한 아이디어로 상품 리스트를 조회
  - API 명세
     - Http Method: `GET`
     - 엔드포인트: `/v1/finder/categories/products/min-max-price`
     - ### Request Param (query string)
       - | 필드명                 | 데이터 타입 | 옵셔널 여부 |
         |---------------------|--------|--------|
         | **categoryName**    | String | **필수** |
     - ### Response body(예제)
       - <details>
         <summary>접기/펼치기</summary>
         
         ```json
         {
          "카테고리": "바지",
          "최저가": [
             {
                 "브랜드": "D",
                 "가격": 3000
             } 
             ],
          "최고가": [
             {
                 "브랜드": "A",
                 "가격": 4200
             }
             ]
             }
         ```
       </details>
4. 브랜드, 상품 추가/수정/삭제 API
    - 브랜드 추가
      - API 명세
          - Http Method: `POST`
          - 엔드포인트: `/v1/brands`
          - ### Request body
            - | 필드명              | 데이터 타입 | 옵셔널 여부 | 타입   |
              |------------------|--------|--------|------|
              | **brandName**    | String | **필수** | Body |
        
          - ### Response body(예제)
            - ```json
              {
                "value": {
                   "brandId": 10,
                   "brandName": "BB"
                }
              }
              ```
   - 브랜드 수정
       - API 명세
           - Http Method: `PUT`
           - 엔드포인트: `/v1/brands/{brandId}`
           - ### Request body & Path variable
             - | 필드명           | 데이터 타입 | 옵셔널 여부  | 타입    |
               |---------------|--------|---------|-------|
               | **brandId**   | Long   | **필수**  | Path  |
               | **brandName** | String | **필수**  | Body  |
  
           - ### Response body(예제)
               - ```json
                 {
                 "value": {
                   "brandId": 11,
                   "brandName": "CC"
                 }
                 }
                 ```
   - 브랜드 삭제
       - API 명세
           - Http Method: `DELETE`
           - 엔드포인트: `/v1/brands/{brandId}`
           - ### Request body & Path variable
             - | 필드명           | 데이터 타입 | 옵셔널 여부  | 타입    |
               |---------------|--------|---------|-------|
               | **brandId**   | Long   | **필수**  | Path  |
         
           - ### Response body(예제)
             - 없음 ( 204 No content)
   - 상품 추가
       - API 명세
           - Http Method: `POST`
           - 엔드포인트: `/v1/products`
           - ### Request body & Path variable
             - | 필드명            | 데이터 타입     | 옵셔널 여부 | 타입   |
               |----------------|------------|--------|------|
               | **brandId**    | Long       | **필수** | Body |
               | **categoryId** | Long       | **필수** | Body |
               | **price**      | BigDecimal | **필수** | Body |

         - ### Response body(예제)
             - ```json
                {
                  "value": {
                  "productId": 73,
                  "categoryName": "아우터",
                  "brandName": "A",
                  "price": 10000
                  }
                }
                ```
   - 상품 수정
       - API 명세
           - Http Method: `PUT`
           - 엔드포인트: `/v1/products/{productId}`
           - ### Request body & Path variable
             - | 필드명            | 데이터 타입     | 옵셔널 여부 | 타입   |
               |----------------|------------|--------|------|
               | **productId**  | Long       | **필수** | Path |
               | **brandId**    | Long       | 선택     | Body |
               | **categoryId** | Long       | 선택     | Body |
               | **price**      | BigDecimal | 선택     | Body |
         
           - ### Response body(예제)
             - ```json
                   {
                      "value": {
                      "productId": 74,
                      "categoryName": "가방",
                      "brandName": "G",
                      "price": 2000
                      }
                   }
               ```
   - 상품 삭제
       - API 명세
           - Http Method: `DELETE`
           - 엔드포인트: `/v1/products/{productId}`
           - ### Request body & Path variable
             - | 필드명           | 데이터 타입 | 옵셔널 여부  | 타입    |
               |---------------|--------|---------|-------|
               | **productId** | Long   | **필수**  | Path  |
  
           - ### Response body(예제)
             - 없음 ( 204 No content)