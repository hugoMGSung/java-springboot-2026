# java-springboot-2026

## 12일차

### ToyProject - StudyGroup

#### 스터디모집 DB설계

- 스터디모집 ERD
  ![alt text](image-39.png)

- 테이블 관계
  - 스터디 종류 카테고리 1개는 여러개의 스터디글에 포함
    - `categories 1 : N study_posts`
  - 사용자 1명은 여러개의 스터디글을 쓸 수 있음
    - `user_account 1 : N study_posts`
  - 사용자 1명은 여러개의 댓글을 쓸 수 있음
    - `user_account 1 : N comments`
  - 스터디 게시글 1개에는 여러개의 댓글이 적힘
    - `study_posts 1 : N comments`
  - 사용자 1명은 여러 스터디 게시글에 신청가능
    - `user_account 1 : N study_applications`
  - 스터디 게시글 1개에는 여러 신청이 들어옴
    - `study_posts 1: N study_applicaitons`

#### 스터디모집 웹사이트

```
StudyGroup
├─ config : 회원가입,로그인시 암호화
├─ controller : MVC 패턴 중 Controller 영역
├─ dto : MVC 패턴 중 Model에 직접연관(DB 테이블 매핑)
├─ mapper : MVC 패턴 중 Model. DB 쿼리 매핑
├─ service : MVC 패턴 중 Model. 비즈니스(도메인) 로직
├─ validation : MVC 패턴 중 View. 화면 입력 검증
└─ resources : 웹페이지 리소스
    ├─ mapper : MVC 패턴 중 Model. DB 쿼리 위치
    ├─ static : View에 포함되는 이미지, CSS, 정적HTML, js 위치
    └─ templates : MVC 패턴 중 VIew. 실제 화면을 나타낼 영역
```

- 카테고리 CRUD
  - dto, Category 클래스 생성
  - validation, CategoryForm 클래스 생성
  - mapper, CategoryMapper 인터페이스, xml 생성
  - service, CategoryService 클래스 생성
  - controller, Admin용 CategoryController 클래스 생성
  - templates/admin/category/list.html, form.html 생성

  ![alt text](image-40.png)
  - 수정, 삭제 기능 완료

- 스터디포스트 CRUD
  - dto, StudyPost 클래스 생성
  - mapper, StudyPostMapper 인터페이스, xml 생성
  - valiation, StudyPostForm 클래스 생성. dto, StudyPost 멤버변수 복사 사용
  - service, StudyPostService 클래스 생성
  - controller, StudyPostController 클래스 생성
  - templates/post/list.html, form.html 생성

  ![alt text](image-41.png)

#### 조회수 증가

- 스터디포스트 상세보기 확인

## 13일차

#### 스터디모집 기능

- 스터디포스트 아래 댓글기능
  - dto, Comment 클래스
  - validation, CommentForm 클래스
  - mapper, CommentMapper 인터페이스
  - templates/mapper, CommentMapper.xml SQL
  - service, CommentService 클래스
  - controller, CommentController 클래스
  - controller, StudyPostController.detail() 댓글 목록, 폼 추가
  - html, post/detail.html 화면 추가

  ![alt text](image-42.png)

- 스터디신청 기능
  - dto, StudyApplication 클래스
  - validation, StudyApplicationForm 클래스
  - mapper, StudyApplicationMapper 인터페이스
  - templates/mapper, StudyApplication.xml
  - service, StudyApplicationService 클래스
  - controller, StudyApplicaitonController 클래스
  - html, post/detail.html 화면 추가

## 14일차

#### 스터디모집 신청 계속

#### TIP

- Controller는 사용자의 요청을 받아서 Service로 전달한 뒤 받은 결과를 View로 출력하는 기능. 로그인세션 처리
- Service는 요청에서 Model로 데이터 요청, 돌려받아서 비즈니스로직 처리
- View는 돌려받은 데이터들을 표현

![alt text](image-44.png)

#### 필요 이슈

- [x] 컨트롤러 post 메서드 파라미터 순서 중요
  - 입력검증 파라미터 다음에 BingResult가 위치해야 함!
  - @Valid CommentForm commentForm, BindingResult bindingResult, ...
- [x] 스터디 신청 문제 - 신청리스트 띄워서 일단 반정도 완료
  - 중복신청 알림 없음
  - 신청 후 메시지 없음
- [x] 각 입력폼 에러메시지 디자인 통일
  - 글로벌 에러는 alert 디자인으로
  - 각 입력별 에러메시지는 단순 빨간색으로
- [x] 전체 인원이 2명인데 3명 승인 가능
- [x] 승인한 멤버에 대해서 다시 거절하는 기능
- [x] 인원이 전부 신청승인되고나면 스터디포스트 자체 상태가 CLOSED 가 되어야 함
- [x] 마감된 스터디에 신청버튼이 존재

- [x] 스터디포스트 페이징
  - BoardMapper.xml 참조해서 StudyPostMapper.xml findAll 메서드 변경
  - StudyPostMapper 인터페이스 위 내용참조해서 추가변경
  - BoardServiceImpl 클래스 참조해서 StudyPostService 클래스 getPostList 메서드 변경
  - StudyPostController 클래스 수정
  - templates/post/list.html 페이징 추가
- [x] Join, Login.html 버튼 디자인 변경

- [x] 게시판 작성자 입력 불필요
  - dto.BoardForm @NotBlank 어노테이션 삭제
- [x] 게시판 댓글 등록 오류메시지 미출력
  - RedirectAttributes 파라미터 사용
- [x] 기존 게시판 상세 디자인 StudyPost 상세 형태와 동일하게 변경
- [x] 로그아웃 후 home으로 이동
- [x] 전체 푸터 작업
  - Bootstrap 클래스만으로 가능

![alt text](image-45.png)

## 15일차

### StudyGroup 계속

#### 관리자 홈관리화면

- 컨텐츠 관리
  - Site_Content 테이블 생성
  - dto, Site 클래스
  - validation, SiteForm 클래스
  - mapper, SiteMapper 인터페이스
  - templates/mapper, SiteMapper.xml
  - service, SiteService 클래스
  - controller, SiteController 클래스
  - controller, HomeController home 메서드 수정

  ![alt text](image-48.png)

- 이미지 관리
  - application.properties 에 저장경로 설정!
  - config, FileProperties 클래스 추가

    ![alt text](image-49.png)

  - config, WebMvcConfig 클래스 추가
  - Site_Image 테이블 생성
  - dto, SiteImage 클래스
  - validation, SiteImageForm 클래스
  - mapper, SiteImageMapper 인터페이스
  - resources/mapper, SiteImageMapper.xml

## 16일차

### StudyGroup 계속

#### 관리자 홈관리 중 이미지 처리

- 이미지 관리 계속
  - service, SiteImageService 클래스
  - controller, SiteImageController 클래스
  - controller, HomeController home 메서드 수정
  - templates/admin/siteImage list.html, form.html 작업

  ![alt text](image-50.png)

#### 홈화면 이미지 표시

- 이미지 표시
  - mapper, SiteImageMapper findAllActive() 메서드 추가, xml 추가
  - service, SiteImageService 메서드 변경
  - home, HomeController home 메서드 로직 변경

  https://github.com/user-attachments/assets/3adda6c9-ce3c-4755-b789-60f1d3fb993a

#### 추가개발 이슈

- [ ] 댓글 삭제 확인창 띄우기
- [ ] Footer 영역, Privacy(개인정보처리방침), Terms(정책) 추가 개발필요
- [ ] 각 입력태그에 PlaceHolder 추가
- [ ] 게시판 댓글 작성자 로그인 아이디 바로 표시하게

- [ ] Features, Gallary 부분 관리자 데이터 처리, 홈화면 이미지 표시
  - Carousel 기능과 동일하게 구현

  ![alt text](image-51.png)

  ![alt text](image-52.png)

- [ ] 게시판 첨부파일 추가
- [ ] 관리자 사이트컨텐츠 등록화면, 컨텐츠키를 콤보박스로 변경해보기
- [ ] 관리자 사이트이미지 등록화면, 이미지키를 콤보박스로 변경해보기
- [ ] 회원가입시 이메일이나 주소등 추가 등록데이터 입력
- [ ] 로그인 후 비번변경이나 개인정보 수정화면

### Spring Security

#### 개요

- Spring 기반 애플리케이션 인증(Authentication), 권한(Authorization)을 담당하는 보안 프레임워크
  - 인증 : 로그인 기능, 세션처리, CSRF/CORS 보안처리
  - 권한 : 접근제어, 글쓰기 가능여부

- 기본동작
  - 요청 -> 필터체인통과
  - 인증여부 확인
  - 미 로그인시 로그인페이지로 이동
  - 로그인 성공 후 세션에 사용자 정보 저장

#### 진행순서

- 의존성 추가
- 비밀번호 암호화 PasswordEncoder 등록
- CustomUserDetails 생성
- UserDetailsSevervice 생성
- SecurityConfig 생성
- 기존 UserController 수정
- 로그인 페이지 수정
- layout.html SpringSecurity thymeleaf 추가
  - session.loginUser 제거
  - sec:authorize 속성으로 변경
- Thymeleaf 로그인/관리자 조건 처리

  ```html
  <!-- 제거 -->
  <div th:if="${#fields.hasGlobalErrors()}" class="alert alert-danger">
    <p th:each="err : ${#fields.globalErrors()}" th:text="${err}"></p>
  </div>
  ```

- Controller에서 HttpSession 파라미터 제거
  - @AuthenticationPrincipal CustomUserDetails loginUser 로 변경
  - 코드 상 LoginUser... 부분 주석처리

#### Spring Security 개발

- build.gradle 의존성 추가
- 실행화면

  ![alt text](image-53.png)

## 17일차

### Spring Security

#### build.gradle 적용

- 서버 실행

  ```powershell
  2026-04-27T09:19:15.043+09:00  WARN 25872 --- [studygroup] [  restartedMain] .s.a.UserDetailsServiceAutoConfiguration :
  # User 임시 패스워드
  Using generated security password: 2e87fa31-bb87-4b0f-afbb-db975c31dc00

  This generated password is for development use only. Your security configuration must be updated before running your application in production.
  ```

- Spring Security Crpto 라이브러리 -> 제거

  ![alt text](image-54.png)

### JWT

#### 개요

- JSON Web Token : 로그인 후에 서버에서 발급하는 토큰 기반의 인증방식
  - React, Node.js 등의 다른 프론트엔드와 연계하는 풀스택개발시 사용하는 인증방식
  - 서버에 세션을 저장안함. 토큰으로 인증 대체

#### JWT 반영순서

- 로그인 > JWT 발급 > 요청 시 JWT 검증 > 인증처리

#### 진행순서

- build.gradle 의존성 추가
  - https://mvnrepository.com/ 에서 확인
- application.properties JWT 설정 추가
- config, JwtTokenProvider 클래스 생성

- dto/api, API 요청/응답용 dto 생성
- config, JwtAuthenticationFilter 클래스 생성
- controller, ApiAuthController 클래스 생성
- config, SecurityConfig 수정

- 테스트 콘트롤러

## 18일차

### JWT 계속

#### CORS, CSRF

- CORS : Cross-Origin Resource Sharing 프로토콜
  - 서로 다른 오리진(서버)에서 리소스나 상호작용을 위해 브라우저에서 실행되는 스크립트
  - 서버간에 통신시 기본 보호 기능
  - com.pknu26.studygroup, com.pknu6.apiboard 둘 사이에 접근불가
  - CORS로 오픈 설정 후

- CSRF : Cross-Site Request Forgery 보안
  - 명시적 동의없이 사용자를 대신 웹앱에서 악의적인 행동을 취하는 공격

#### API 테스트

- Postman 테스트

  ![alt text](image-55.png)
  - 로그인 실패하면 로그인화면으로 다시 돌아감
  - 성공하면 json를 리턴

  ```json
  {
    "tokenType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJwa251IiwidXNlcklkIjo0LCJuYW1lIjoi67aA6rK964yAIiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTc3NzM0NDU4OCwiZXhwIjoxNzc3MzQ4MTg4fQ.2s2TR4SXQUJWFIy2mMx-j9OwQd3iZIl5S7SW6U0xNfMIG-KH3b7Xe7rBZGMX8m8t",
    "userId": 4,
    "loginId": "pknu",
    "name": "부경대",
    "role": "ROLE_USER"
  }
  ```

### 소셜 로그인

#### 구글 로그인

```text
USER_ACCOUNT
 └─ StudyGroup 사용자 계정

Spring Security Form Login
 └─ /user/login

JWT API Login
 └─ /api/auth/login

추가할 Google Login
 └─ /oauth2/authorization/google
 └─ 성공 후 USER_ACCOUNT + USER_SOCIAL_ACCOUNT 저장
```

#### OAuth

- Open Authorization : 아이디와 패스워드를 넘겨주지 않고, 다른 서비스의 기능을 안전하게 빌려쓰는 기술
  - 구글, 네이버, 카카오, 페이스북, ...

- OAuth 1.0 : 암호화방식 너무 복잡(암호화 지옥), 사용하기 어려움.
- OAuth 2.0 : 복잡한 서명 삭제, 역할분담, 유연한 처리 가능

#### 소셜 로그현 구현

- build.gradle 에 의존성 추가
- 구글 개발자콘솔 로그인 : https://console.cloud.google.com/
  - 새 프로젝트 생성

  ![alt text](image-56.png)
  - 프로젝트 선택 > 탐색메뉴(햄버거메뉴)
  - API 및 서비스
    - 사용자 인증 정보 > +사용자 인증정보 만들기 클릭
  - OAuth 동의화면 클릭 > 시작하기 클릭
  - OAuth 클라이언트 ID 클릭

  ![alt text](image-57.png)
  - 작성 후 만들기 클릭

  ![alt text](image-58.png)
  - json 다운로드

- application.properties 구글OAuth 정보 추가

- powershell에서 구글클라이언트아이디와 비밀키를 윈도우 환경변수에 등록

  ```powershell
  # 설정
  > setx GOOGLE_CLIENT_ID "본인_구글클라이언트_아이디"

  성공: 지정한 값을 저장했습니다.
  > PS C:\Users\Admin> setx GOOGLE_CLIENT_SECRET "본인_구글클라리언트_시크릿키"

  성공: 지정한 값을 저장했습니다.

  # 확인 파워쉘 재시작 후
  > echo $env:GOOGLE_CLIENT_ID
  > echo $env:GOOGLE_CLIENT_SECRET
  ```

- 소셜 로그인 연결용 테이블 생성
- 기존 로그인 테이블 password 필드 NOT NULL -> NULL 로 변경(소셜로그인으로는 패스워드 전달안됨)
- LOGIN_ID 길이 변경 VARCHAR2(200). 이메일 입력

- dto, UserSocialAccount 클래스
- mapper, UserSocialAccountMapper 인터페이스
- resources/mapper, UserSocialAccountMapper.xml
- mapper, UserMapper 인터페이스에 신규 메서드 추가
- resources/mapper, UserMapper.xml 신규 SQL 추가

- OAuth2 구글로그인서비스 클래스
  - security, CustomOAuth2UserService 클래스
    1. 구글 사용자 정보받기
    2. provider = google
    3. providerUserId = 구글 Subject
    4. USER_SOCIAL_ACCOUNT 테이블에 이미 정보가 있으면 로그인처리
    5. 없으면 USER_ACCOUNT 생성
    6. USER_SOCIAL_ACCOUNT 생성
    7. Spring Security 인증 처리

- config, SecurityConfig에 OAuth2 Login 추가

  ![alt text](image-60.png)

  ![alt text](image-59.png)

- Google 환경 로그
  ![alt text](image-61.png)

- 구글 로그인

  ![alt text](image-62.png)

  ![alt text](image-63.png)

### 남은 이슈

- [x] favicon 추가
  - 자동인식방법 resources/static/favicon.ico
  - png to ico 변환필요

  ![alt text](image-46.png)

- [x] 에러페이지 필요 - 디자인만 잘하면 됨
  - 404 에러 : Page Not Found
  - 500 에러 : Internel Server Error

- [x] home.html 관리자 관리할 화면 생성
  - Hero 이미지 : 웹 전체 화면을 채우는 배경이미지
  - Carousel : 이미지가 일정시간마다 전환, 또는 버튼클릭으로 전환되는 디자인
  - 현재 화면

  ![alt text](image-47.png)
  - 파일 업로드

- [x] 세군데 있던 checkAdmin 메서드 정리. AdminHelper 클래스 생성
- [ ] 게시판 용량 큰 이미지(4.5MB 정도)를 붙여넣은 후 저장 오류

- 미니프로젝트 팀 구성
- 미니프로젝트 주제
