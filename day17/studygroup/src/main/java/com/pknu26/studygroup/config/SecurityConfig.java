package com.pknu26.studygroup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/",  // http://localhost:8080/
                "/user/join",
                "/user/login",
                "/css/**",
                "/images/**",
                "/js/**",
                "/trumbowyg/**",
                "/upload/**"
            ).permitAll()   // 로그인없이 접속할 수 있는 권한 부여
            
            .requestMatchers("/admin/**").hasRole("ADMIN")  // ROLE_ADMIN 에게만 /admin/ 접속권한
            
            .requestMatchers(
                "/studypost/create",
                "/studypost/edit/**",
                "/studypost/delete/**",
                "/comment/**",
                "/application/**",
                "/board/create",
                "/board/edit/**",
                "/board/delete/**"
            ).authenticated()    // 위의 URL은 로그인한 사용자에게만 접속권한
            .anyRequest().permitAll()            
        )
        .formLogin(form -> form
            .loginPage("/user/login")
            .loginProcessingUrl("/user/login")
            .usernameParameter("loginId")
            .passwordParameter("password")
            .defaultSuccessUrl("/studypost/list", true)  // 접속 성공 후 이동하는 첫페이지
            .failureUrl("/user/login?error=true")  // 로그인 에러났을때 화면
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )
        ;

        return http.build();
    }
}
