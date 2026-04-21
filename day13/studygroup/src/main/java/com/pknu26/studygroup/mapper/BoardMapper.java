package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.studygroup.dto.Board;

// resources/mapper/BoardMapper.xml과 SQL문과 매핑!
@Mapper
public interface BoardMapper {
    
    List<Board> findAll(@Param("offset") int offset, @Param("size") int size);

    // 페이징용 전체 게시글 갯수
    int getTotalCount();

    Board findById(Long boardId);

    // #PRC13 - 얘도 인터페이스니까 어딘가에 로직이 작성된게 있다
    int insertBoard(Board board);

    int updateBoard(Board board);

    int deleteBoard(Long boardId);
} 
