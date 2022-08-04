package com.aerotravel.flightticketbooking.oauth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;


//@RestController
@Controller
@AllArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    /**
     * 카카오 callback
     * [GET] /oauth/kakao/callback
     */
    @Autowired
    private OAuthService kakao;


    @ResponseBody
    @GetMapping("/kakaotest")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println(code);
    }

    /*
    @RequestMapping(value="/kakaologin")
    public String login(@RequestParam String code) {

        String access_Token = kakao.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        kakao.createKakaoUser(access_Token);

        return "로그인 테스트";
    }

     */

    //@RequestMapping(value="/kakaologin")
    @PostMapping(value="/kakaologin")
    public String login(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakao.getKakaoAccessToken(code);
        HashMap<String, Object> userInfo = kakao.createKakaoUser(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }
        System.out.println("Session: "+ session);
        return "redirect:/";
    }



}
