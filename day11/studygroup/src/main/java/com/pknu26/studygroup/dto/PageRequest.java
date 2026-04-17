package com.pknu26.studygroup.dto;

import lombok.Data;

// 페이지 요청할때 필요한 값 계산
@Data
public class PageRequest {

    private int page = 1;  /// 현재 페이지, 1, 2, 3 
    private int size = 10; // 한 페이지당 게시글 수

    /**
     * page 1 : (1 - 1) * 10 = 0
     * page 2 : (2 - 1) * 10 = 10
     * page 3 : (3 - 1) * 10 = 20
     */
    public int getOffset() {
        return (page - 1) * size; 
    }
}
