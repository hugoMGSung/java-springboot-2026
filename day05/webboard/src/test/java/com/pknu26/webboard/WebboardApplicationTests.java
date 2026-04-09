package com.pknu26.webboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// main.java 밑에 작업한 실제 클래스/인터페이스
import com.pknu26.webboard.entity.Board;
import com.pknu26.webboard.repository.BoardRepository;

@SpringBootTest
class WebboardApplicationTests {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testBoardJpa() {
		Board b1 = new Board();
		b1.setTitle("첫번째 게시판 글 테스트입니다.");
		b1.setContent("첫번째 게시글입니다. 반갑습니다.");
		b1.setCreateDate(LocalDateTime.now()); 

		this.boardRepository.save(b1);  // INSERT INTO 문 실행

		Board b2 = new Board();
		b2.setTitle("두번째 게시판 글 테스트입니다.");
		b2.setContent("두번째 게시글입니다.");
		b2.setCreateDate(LocalDateTime.now()); 

		this.boardRepository.save(b2);  // INSERT 
	}

	@Test
	void testSelectOneJpa() {
		// Optional => NULL 허용
		// SELECT * FROM board WHERE bno = 3;
		Optional<Board> opBoard = this.boardRepository.findById(3L); // Board테이블에서 3번 게시글 		
		if (opBoard.isPresent()) { // 
			Board board = opBoard.get(); 
			assertEquals("두번째 게시글입니다.", board.getContent()); // 두 인자가 같은 값이면 true가 출력.
		}
	}
}
