-- student 테이블
CREATE TABLE student (
	id NUMBER(10) PRIMARY KEY,
	name VARCHAR2(100) NOT NULL,
	age NUMBER(3),
	major VARCHAR2(100)
);

-- 시퀀스
CREATE SEQUENCE student_seq
START WITH 1
INCREMENT BY 1
nocache;

-- 샘플 데이터
INSERT INTO student VALUES (student_seq.nextval, '홍길동', 20, '컴퓨터공학');
INSERT INTO student VALUES (student_seq.nextval, '이영희', 22, '전자공학');

COMMIT;

