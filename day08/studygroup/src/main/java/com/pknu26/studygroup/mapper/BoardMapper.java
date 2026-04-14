package com.pknu26.studygroup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.Board;

// resources/mapper/BoardMapper.xml과 매핑!
@Mapper
public interface BoardMapper {
    
    List<Board> findAll();

    Board findById(Long boardId);

    int insertBoard(Board board);

    int updateBoard(Board board);

    int deleteBoard(Long boardId);
} 
