package com.pknu26.studygroup.dto;

import java.util.List;

import lombok.Getter;

/**
 * PageReponse<T>
 * 
 * - 화면에 보여줄 '현재 페이지 데이터 리스트'
 * - 페이지 번호 출력에 필요한 계산결과 
 * 
 * 필요정보
 * - 현재 페이지에 보여줄 게시글 리스트
 * - 전체 게시글 수
 * - 현재 페이지번호
 * - 전체 페이지수
 * - 페이지번호 시작값, 끝값
 * - 이전, 다음 페이지 표시 여부
 * 
 * 제네릭 T
 * Board외에 다른 dto도 사용할 수 있도록
 */
@Getter
public class PageResponse<T> {

    private final List<T> dtoList;  // 게시글 리스트 T : Board, Student, Reply등 모두 가능
    private final int totalCount;   // 전체 게시글 수 예) 32
    private final int currentPage;  // 사용자가 보고있는 현재 페이지 번호
    private final int size;         // 페이지당 게시글 수 10, 
    private final int totalPage;    // 전체 페이지수. 게시글이 32 / 10 = 3.2 를 올림해서 4페이지 필요 

    /**
     * 1, 11이 시작페이지
     * 페이지 번호 묶음
     * [1][2][3][4][5][6][7][8][9][10]
     * [11][12][13][14][15][16][17][18][19][20]
     */
    private final int startPage;    // 시작페이지 , 화면에 보여줄 시작페이지
    private final int endPage;      // 화면에 보여줄 마지막페이지
    private final boolean prev;     // 이전버튼 표시 여부
    private final boolean next;     // 끝버튼 표시 여부

    public PageResponse(List<T> dtoList, int totalCount, int currentPage, int size) {
        this.dtoList = dtoList;        // 페이징된 현재 페이지의 실제 게시글 리스트
        this.totalCount = totalCount;   // 전체 데이터 개수
        this.currentPage = currentPage;     // 현재 페이지 
        this.size = size;                   // 단위 사이즈 10

        // 37 / 10 = 3.7 올림 4
        this.totalPage = (int) Math.ceil((double) totalCount / size); 

        // 현재 페이지가 속한 페이지 묶음의 끝번호
        /**
         * 1~10   tempEnd = 10
         * 11~20  tempEnd = 20
         */
        int tempEnd = (int) (Math.ceil(currentPage / 10.0) * 10);

        // 20 - 9 = 11
        this.startPage = tempEnd - 9;
        /**
         *  totalPage 37 / 10 = 4 
         *  tempEnd 1 ~ 10 
         */
        this.endPage = Math.min(tempEnd, totalPage);

        // 현재 페이지 번호 묶음의 시작이 1보다 크면 
        // 앞쪽에 다른 페이지 묶음이 존재한다
        this.prev = startPage > 1;  
        // 현재 페이지 번호 묶음 끝 보다 전체 페이지 수가 크면
        // 뒤에 다른 페이지 묶음이 존재한다
        this.next = totalPage > tempEnd;
    }
}
