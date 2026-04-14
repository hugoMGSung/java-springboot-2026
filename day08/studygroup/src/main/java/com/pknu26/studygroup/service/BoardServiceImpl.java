package com.pknu26.studygroup.service;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.mapper.BoardMapper;
import com.pknu26.studygroup.validation.BoardForm;

import lombok.RequiredArgsConstructor;

// BoardService 인터페이스 구현 클래스
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public void createBoard(BoardForm boardForm) {
        Board board = new Board();
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setWriter(boardForm.getWriter());
        // board.setCreatedAt(LocalDateTime.now()); // SYSDATE default 때문에 생략가능

        this.boardMapper.insertBoard(board);
    }

    @Override
    public List<Board> readBoardList() {
        return this.boardMapper.findAll();
    }

    @Override
    public Board readBoardById(Long boardId) {
        return this.boardMapper.findById(boardId);
    }

    @Override
    public void updateBoard(BoardForm boardForm) {
        Board board = new Board();
        board.setBoardId(boardForm.getBoardId());
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        board.setWriter(boardForm.getWriter());
        // board.setUpdatedAt(LocalDateTime.now()); // SYSDATE default 때문에 생략가능

        this.boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(Long boardId) {
        this.boardMapper.deleteBoard(boardId);
    }
}
