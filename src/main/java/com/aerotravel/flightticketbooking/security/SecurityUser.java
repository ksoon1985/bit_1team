package com.aerotravel.flightticketbooking.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class SecurityUser extends User implements OAuth2User {
    private static final long serialVersionUID = 1L;
    private com.aerotravel.flightticketbooking.model.User user;
    private Map<String, Object> attributes;

    public SecurityUser(com.aerotravel.flightticketbooking.model.User user){
        super(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().get(0).getName()));
        this.user = user;
    }

    public SecurityUser(com.aerotravel.flightticketbooking.model.User user, Map<String, Object> attributes ){
        super(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().get(0).getName()));
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        String sub = attributes.get("sub").toString();
        return sub;
    }
}
