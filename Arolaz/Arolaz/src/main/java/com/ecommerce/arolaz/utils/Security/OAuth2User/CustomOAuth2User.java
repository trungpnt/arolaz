//package com.ecommerce.arolaz.utils.Security.OAuth2User;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.Collection;
//import java.util.Map;
//
//public class CustomOAuth2User implements OAuth2User {
//
//    private OAuth2User oAuth2User;
//
//    private String email;
//
//    public CustomOAuth2User(OAuth2User loadUser) {
//        oAuth2User = loadUser;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return oAuth2User.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return oAuth2User.getAuthorities();
//    }
//
//    @Override
//    public String getName() {
//        return oAuth2User.getAttribute("name");
//    }
//
//    public String getFullName() {
//        return oAuth2User.getAttribute("name");
//    }
//
//    public String getEmail(){
//        return oAuth2User.getAttribute("email");
//    }
//}
