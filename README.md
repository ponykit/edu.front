# EDU Front - 온라인 교육 플랫폼 프론트엔드

온라인 강의 수강, 게시판, 장바구니 기능을 제공하는 교육 플랫폼의 프론트엔드 웹 애플리케이션입니다.
Spring Boot + Thymeleaf 기반의 SSR(Server-Side Rendering) 구조로 구현하였습니다.

---

## 기술 스택

| 분류 | 기술 |
|------|------|
| Backend | Java 11, Spring Boot 2.3.3 |
| Template Engine | Thymeleaf, Thymeleaf Layout Dialect |
| ORM | MyBatis 2.1.3 |
| Database | Microsoft SQL Server |
| Build | Gradle |
| 기타 | Lombok, PageHelper, Log4jdbc, jBcrypt |

---

## 주요 기능

### 강의
- 메인 페이지: 카테고리별 인기 강의, 자격증 과정, 신규 강의 노출
- 강의 목록: 키워드·날짜 필터링, 페이징 처리
- 강의 상세: 강의 정보 및 커리큘럼(강의 단위별 파일 목록) 조회

### 게시판
- 게시글 목록: 검색(제목·날짜·카테고리), 페이징 처리
- 게시글 상세 조회
- 댓글 작성 및 댓글 목록 (HTMX-friendly 프래그먼트 응답)

### 공통
- 카테고리·공통코드 메뉴 인젝션 (Interceptor 적용)
- 파일 미리보기 / 이미지 서빙 API
- 전역 예외 처리 (`@ControllerAdvice`)

---

## 프로젝트 구조

```
src/main/java/com/edu/front/
├── config/
│   ├── DatabaseConfig.java          # MyBatis DataSource 설정
│   ├── GlobalExceptionHandler.java  # 전역 예외 처리
│   └── WebConfig.java               # 인터셉터 등록
├── controller/
│   ├── MainController.java          # 메인 페이지
│   ├── FrontController.java         # 강의·장바구니
│   ├── BoardController.java         # 게시판
│   ├── MemberController.java        # 마이페이지
│   └── RestCommonController.java    # 파일 미리보기 REST API
├── service/
│   ├── FrontService.java            # 강의·게시판 비즈니스 로직
│   ├── CommonService.java           # 카테고리·공통코드 조회
│   └── FileStorageService.java      # 파일 로드 (Path Traversal 방지)
├── dao/
│   ├── FrontDao.java
│   └── CommonDao.java
├── model/
│   ├── course/Course.java
│   ├── course/CourseDetail.java
│   └── common/
├── interceptor/
│   └── MenuListInjectInterceptor.java
└── util/
    ├── Pagination.java
    ├── StringUtil.java
    └── DateUtil.java

src/main/resources/
├── mapper/
│   ├── front.xml                    # 강의·게시판 SQL
│   ├── common.xml                   # 공통 SQL
│   └── PaginationMapper.xml         # 공통 페이징 CTE
└── templates/pages/
    ├── index.html
    ├── courses/
    ├── board/
    └── order/
```

---

## 실행 방법

### 1. 사전 요구사항

- JDK 11 이상
- Microsoft SQL Server (스키마는 별도 제공)
- Gradle (또는 `./gradlew` 사용)

### 2. 환경변수 설정

아래 환경변수를 설정하거나 `application.yml`의 기본값을 수정하세요.

```bash
export DB_URL=jdbc:log4jdbc:sqlserver://localhost:1433;databaseName=edu
export DB_USERNAME=your_db_user
export DB_PASSWORD=your_db_password
export UPLOAD_PATH=/your/upload/path
```

또는 프로젝트 루트에 `.env` 파일을 생성하세요 (`.gitignore`에 포함되어 있습니다).

### 3. 빌드 및 실행

```bash
# 빌드
./gradlew build

# 로컬 프로필로 실행
./gradlew bootRun --args='--spring.profiles.active=local'

# 또는 JAR 실행
java -jar build/libs/edu.front-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 4. 접속

```
http://localhost:8080
```

---

## 주요 URL

| URL | 설명 |
|-----|------|
| `/` | 메인 페이지 |
| `/courses/course-list` | 강의 목록 |
| `/courses/course-detail?courseSeq={id}` | 강의 상세 |
| `/board/board-list/{boardType}` | 게시판 목록 |
| `/board/board-detail?BoNum={id}` | 게시글 상세 |
| `/order/cart-list` | 장바구니 |
| `/mypage/myinfo` | 마이페이지 |
| `/common/img/{fileName}` | 이미지 서빙 API |

---

## 구현 포인트

### 공통 페이징 (CTE 방식)
SQL Server의 `WITH ... AS (CTE)` + `OFFSET/FETCH` 구문을 `PaginationMapper.xml`에 공통 SQL 조각(`<sql id="header/footer">`)으로 분리하여 모든 목록 쿼리에서 재사용합니다.

### 메뉴 자동 주입 (Interceptor)
`MenuListInjectInterceptor`가 모든 요청의 `postHandle` 시점에 카테고리·공통코드를 조회하여 모델에 자동 주입합니다. 각 컨트롤러에서 중복 조회할 필요가 없습니다.

### 파일 서빙 보안
`FileStorageService`는 요청된 파일 경로가 업로드 루트 디렉터리 내에 있는지 `Path.startsWith()`로 검증하여 Path Traversal 공격을 방지합니다.

### SQL Injection 방지
MyBatis 매퍼에서 사용자 입력값은 모두 `#{param}` (PreparedStatement) 형식을 사용합니다.
`ORDER BY`에 사용되는 컬럼명/방향은 `${param}` 이 불가피하므로 허용된 값 목록(화이트리스트)으로 검증이 필요합니다.

---

## 환경 프로필

| 프로필 | 설명 |
|--------|------|
| `local` | 파일시스템 템플릿, Thymeleaf 캐시 OFF |
| `prod` | 클래스패스 템플릿, Thymeleaf 캐시 ON, DevTools OFF |
