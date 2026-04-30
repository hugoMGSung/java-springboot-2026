package com.pknu26.studygroup.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pknu26.studygroup.dto.User;

@Mapper
public interface UserMapper {

    User findByLoginId(String loginId);

    int insertUser(User user);

    // 240429 소셜로그인 추가
    Long nextUserId();  // UserAccount에서 만들어진 UserId PK값을 가지고 UserSocialAccount 테이블에 FK로 입력

    User findByEmail(String email);

    void insertSocialUser(User user);  // 260430. insertSocialuser -> insertSocialUser 오타
}