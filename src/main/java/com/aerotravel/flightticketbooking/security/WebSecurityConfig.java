package com.aerotravel.flightticketbooking.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        // custom userDetailService(security) 등록
        security.userDetailsService(userDetailService);

        // url 인증 관련
        security.authorizeRequests()
                .antMatchers("/","/login","/signUp","/flight/search", "/flight/book/cancel", "/img/**", "/css/**", "/js/**","/sample").permitAll()
                .antMatchers( "/flight/book**", "/flight/book/new","/flight/book/verify").authenticated()
                .antMatchers("/**").hasRole("ADMIN");

        // restful 허용
        security.csrf().disable();

        // login security
        security.formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error");

        // logout security
        security.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login?logout");

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