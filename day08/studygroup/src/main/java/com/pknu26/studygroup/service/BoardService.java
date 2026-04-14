package com.pknu26.studygroup.service;

import java.util.List;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.validation.BoardForm;

// 서비스를 구현할때 반드시 아래의 메서드를 전부 구현할 것
public interface BoardService {
    // CRUD = Create(Insert), Read(Select), Update, Delete
    void createBoard(BoardForm boardForm);

    List<Board> readBoardList();

    Board readBoardById(Long boardId);

    void updateBoard(BoardForm boardForm);
    
    void deleteBoard(Long boardId);
} 