package com.pknu26.studygroup.security;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.pknu26.studygroup.dto.User;
import com.pknu26.studygroup.dto.UserSocialAccount;
import com.pknu26.studygroup.mapper.UserMapper;
import com.pknu26.studygroup.mapper.UserSocialAccountMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;
    private final UserSocialAccountMapper userSocialAccountMapper;

    // OAuth2로 로그인 처리 
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService를 사용해서 사용자정보 가져오기
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 로그인제공자가 누구인지 식별(Google, Kakao, Naver...)
        String provider = userRequest
                            .getClientRegistration()
                            .getRegistrationId();

        Map<String, Object> attributes = oauth2User.getAttributes();

        String providerUserId = String.valueOf(attributes.get("sub"));
        String email = String.valueOf(attributes.get("email"));
        String name = String.valueOf(attributes.get("name"));
        String picture = String.valueOf(attributes.get("picture"));

        // 해당 소셜계정이 이미 DB에 있는지 확인
        UserSocialAccount userSocialAccount = 
                userSocialAccountMapper.findByProviderAndProviderUserId(provider, providerUserId);

        User user; // {} local scope때문에 미리 생성

        if (userSocialAccount != null) { // 이미 소셜계정이 존재
            user = userMapper.findByEmail(email);
        } else { // 소셜계정이 아직 없으면, 새로 전부 생성
            // 첫 로그인인 경우 먼저 이메일로 가입된 사용자 확인
            user = userMapper.findByEmail(email);

            if (user == null) { // 기존 사용자도 없음, join 처리
                Long userId = userMapper.nextUserId();  // User_Account 테이블의 PK값을 가져옴

                user = new User();
                user.setUserId(userId);
                user.setLoginId(email); // LoginID 는 email 처리!!
                user.setPassword(null);  // 소셜로그인은 패스워드를 직접처리하지 않음!
                user.setName(name);                          
                user.setRole("ROLE_USER");

                userMapper.insertSocialUser(user); // 기존회원가입(join)과 방식이 다름 // 260430 insertSocialuser 오타
            }

            // 소셜사용자계정도 DB저장
            UserSocialAccount newSocialAccount = new UserSocialAccount();
            newSocialAccount.setUserId(user.getUserId());
            newSocialAccount.setProvider(provider);
            newSocialAccount.setProviderUserId(providerUserId);
            newSocialAccount.setEmail(email);
            newSocialAccount.setName(name);
            newSocialAccount.setProfileImage(picture);

            userSocialAccountMapper.insertSocialAccount(newSocialAccount);
        }

        // Spring Security가 객체를 사용할 수 있도록 리턴
        // 260430. DefaultOAuth2UserDetail -> CustomOAuth2UserDetails 변경해야 세션저장 
        return new CustomOAuth2UserDetails(
                user,
                List.of(new SimpleGrantedAuthority(user.getRole())), 
                attributes, 
                "name"); // sub -> name
    }    
}
