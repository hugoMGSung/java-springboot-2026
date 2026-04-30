package com.pknu26.studygroup.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.pknu26.studygroup.dto.UserSocialAccount;

@Mapper
public interface UserSocialAccountMapper {
    
    UserSocialAccount findByProviderAndProviderUserId(
        @Param("provider") String provider, 
        @Param("providerUserId") String providerUserId
    );

    void insertSocialAccount(UserSocialAccount socialAccount);
}
