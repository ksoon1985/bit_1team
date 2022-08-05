package com.aerotravel.flightticketbooking.security;

import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByUsername(username);
        if(!optional.isPresent()){
            throw new UsernameNotFoundException(username + "사용자 없음");
        }else{
            User user = optional.get();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(user.getRoles().get(0).getName());
            return new SecurityUser(user);
        }
    }


}
