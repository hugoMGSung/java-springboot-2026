package com.pknu26.webboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu26.webboard.entity.Board;

@Repository
// DB에 있는 Board 테이블을 핸들링(CRUD) 쿼리를 자동으로 실행할 수 있는 인터페이스
public interface BoardRepository extends JpaRepository<Board, Long> {
    // INSERT, UPDATE, SELECT, DELETE 쿼리 수행
    // 필요한 기능이 있으면 아래 작성.
}
