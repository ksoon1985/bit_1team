package com.aerotravel.flightticketbooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailService userDetailService;

    @Autowired
    private SecurityOauth2UserService securityOauth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return new BCryptPasswordEncoder();
    }

    /*
    @Configuration
    public class Encoder {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
*/
    @Override
    protected void configure(HttpSecurity security) throws Exception {

        // custom userDetailService(security) 등록
        security.userDetailsService(userDetailService);

        // url 인증 관련
        security.authorizeRequests()
                .antMatchers("/","/login","/signUp/**","/signUp","/highcharts","/flight/search","/sample", "/flight/book/cancel", "/img/**", "/css/**", "/js/**").permitAll()
                .antMatchers( "/flight/book**", "/flight/book/new", "/flight/**","/flight/book/verify").authenticated()
                .antMatchers("/**").hasRole("ADMIN");

        // restful 허용
        security.csrf().disable(); // ajax 통신하기 위해서

        // login security
        security.formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error");

        // logout security
        security.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login?logout");

        // oAuth2
        security.oauth2Login()				// OAuth2기반의 로그인인 경우
                .loginPage("/login")		// 인증이 필요한 URL에 접근하면 /loginForm으로 이동
                .defaultSuccessUrl("/")			// 로그인 성공하면 "/" 으로 이동
                .failureUrl("/login?error")		// 로그인 실패 시 /loginForm으로 이동
                .userInfoEndpoint()			    // 로그인 성공 후 사용자정보를 가져온다
                .userService(securityOauth2UserService);	//사용자정보를 처리할 때 사용한다

        security.exceptionHandling();

    }

    @Autowired
    public void authenticate(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("manager")
                .password("{noop}manager123")
                .roles("AGENT");
        // {noop} : 비밀번호에 암호화 처리를 하지 않겠다.

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin123")
                .roles("ADMIN");
    }
}