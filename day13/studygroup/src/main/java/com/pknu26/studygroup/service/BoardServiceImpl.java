package com.pknu26.studygroup.service;

// import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.Board;
import com.pknu26.studygroup.dto.PageRequest;
import com.pknu26.studygroup.dto.PageResponse;
import com.pknu26.studygroup.mapper.BoardMapper;
import com.pknu26.studygroup.validation.BoardForm;

import lombok.RequiredArgsConstructor;

// BoardService 인터페이스 구현 클래스
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    // #PRC08 - 구현체에서 데이터 입력을 처리하는 서비스(비즈니스 로직)
    @Override
    public void createBoard(BoardForm boardForm) {
        // #PRC10 - Board 객체 새로 만들고
        Board board = new Board();
        // #PRC11 - BoardForm에 있는 데이터를 가져와서 Board에 할당
        board.setTitle(boardForm.getTitle());
        board.setContent(boardForm.getContent());
        // 260417. 작성자 이름대신 LoginID로 변경 수정
        // board.setWriter(boardForm.getWriter());
        board.setWriter(boardForm.getWriterId());
        // board.setCreatedAt(LocalDateTime.now()); // SYSDATE default 때문에 생략가능
        // #PRC12 - BoardMapper에 메서드 호출
        this.boardMapper.insertBoard(board);
    }

    // 페이징용으로 전체 변경
    @Override
    public PageResponse<Board> readBoardList(PageRequest pageRequest) {
        List<Board> boardList = this.boardMapper.findAll(pageRequest.getOffset(), pageRequest.getSize());

        int totalCount = this.boardMapper.getTotalCount();

        // return this.boardMapper.findAll();
        return new PageResponse<>(boardList, totalCount, pageRequest.getPage(), pageRequest.getSize());
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
        // board.setWriter(boardForm.getWriter());
        board.setWriter(boardForm.getWriterId()); // 수정할 때도 아이디가 저장되어야 함
        // board.setUpdatedAt(LocalDateTime.now()); // SYSDATE default 때문에 생략가능

        this.boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(Long boardId) {
        this.boardMapper.deleteBoard(boardId);
    }
}
