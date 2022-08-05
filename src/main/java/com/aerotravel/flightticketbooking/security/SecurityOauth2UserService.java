package com.aerotravel.flightticketbooking.security;
import antlr.BaseAST;
import com.aerotravel.flightticketbooking.model.Role;

import com.aerotravel.flightticketbooking.repository.RoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.aerotravel.flightticketbooking.model.User;
import com.aerotravel.flightticketbooking.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityOauth2UserService extends DefaultOAuth2UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    //@Autowired private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;


        String provider = userRequest.getClientRegistration().getRegistrationId();
        oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId;  			// 사용자가 입력한 적은 없지만 만들어준다

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        //String password = bCryptPasswordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다
        //String password =
        String email = oAuth2UserInfo.getEmail();
        String lastname = oAuth2UserInfo.getName();
        Role role = new Role();
        role.setId(2);
        System.out.println(email);

        User byUsername = userRepository.findByUsername(username);

        if(byUsername == null){
            //DB에 없는 사용자라면 회원가입처리
            byUsername= User.oauth2Register()
                    .username(username).password(password).email(email).lastname(lastname)
                    .provider(provider).providerId(providerId)
                    .build();
            List<Role> roleList = new ArrayList<>();
            Optional<Role> roleOptional = roleRepository.findById(2);
            roleList.add(roleOptional.get());
            byUsername.setRoles(roleList);
            userRepository.save(byUsername);

            // throw new UsernameNotFoundException(username + "사용자 없음");
        }


        return new SecurityUser(byUsername, oAuth2UserInfo.getAttributes());
    }
}
