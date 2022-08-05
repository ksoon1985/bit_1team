package com.aerotravel.flightticketbooking.services;

import com.aerotravel.flightticketbooking.model.User;


public interface UserService {
    public void signUp(User user);
    public boolean usernameChk(String name);
}


