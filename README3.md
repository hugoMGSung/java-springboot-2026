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

#### 필요이슈

- home.html 관리자 관리할 화면 생성
- home.html 동적바인딩
- 로그아웃 후 home으로 이동
- 에러페이지 필요
- Join, Login 페이지 버튼 디자인 변경
- 스터디포스트 페이징
- 전체 푸터 작업
- Spring Security
- JWT
- React와 연동

- 미니프로젝트 팀 구성
- 미니프로젝트 주제
