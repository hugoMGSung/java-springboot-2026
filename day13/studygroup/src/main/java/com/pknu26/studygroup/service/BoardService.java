package com.pknu26.studygroup.service;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.dto.PageRequest;
import com.pknu26.studygroup.dto.PageResponse;
import com.pknu26.studygroup.validation.BoardForm;

// 서비스를 구현할때 반드시 아래의 메서드를 전부 구현할 것
public interface BoardService {
    // CRUD = Create(Insert), Read(Select), Update, Delete
    // #PRC07 - 얘는 인터페이스니까 구현을 찾아야 함
    void createBoard(BoardForm boardForm);

    // 페이징을 위해서 파라미터 추가, 리턴타입 변경
    PageResponse<Board> readBoardList(PageRequest pageRequest);

    Board readBoardById(Long boardId);

    void updateBoard(BoardForm boardForm);
    
    void deleteBoard(Long boardId);
} 