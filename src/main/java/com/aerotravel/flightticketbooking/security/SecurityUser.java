package com.aerotravel.flightticketbooking.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;
    private com.aerotravel.flightticketbooking.model.User user;

    public SecurityUser(com.aerotravel.flightticketbooking.model.User user){
        super(user.getUsername(),user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().get(0).getName()));
        this.user = user;
    }
}
