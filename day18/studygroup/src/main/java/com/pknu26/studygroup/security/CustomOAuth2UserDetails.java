package com.pknu26.studygroup.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.pknu26.studygroup.dto.User;

public class CustomOAuth2UserDetails extends CustomUserDetails implements OAuth2User {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    public CustomOAuth2UserDetails(
            User user,
            Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes,
            String nameAttributeKey) {
        super(user, authorities);
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        Object value = attributes.get(nameAttributeKey);
        return value != null ? value.toString() : getUsername();
    }
}
