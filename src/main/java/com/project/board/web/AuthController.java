package com.project.board.web;

import com.project.board.dto.UserDto;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @GetMapping("/auth/login")
    public String loginForm() {

        return "login";
    }

    @PostMapping("/auth/login")
    public String login() {

        return "login";
    }


    @GetMapping("/auth/join")
    public String joinForm() {

        return "join";
    }

    @PostMapping("/auth/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult) {

        UserEntity userEntity = userDto.toEntity();
        authService.회원가입(userEntity);

        return "redirect:/main";

    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "cd71d24ba09df178e17c6c59fa8289c5");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답" + response;
    }


}
