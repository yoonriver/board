package com.project.board.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board.dto.KakaoProfileDto;
import com.project.board.dto.UserDto;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.role.Role;
import com.project.board.service.AuthService;
import com.project.board.token.OAuthToken;
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
import java.util.UUID;

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

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //System.out.println("카카오 액세스 토큰 : " + oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfileDto kakaoProfileDto = null;

        try {
            kakaoProfileDto = objectMapper2.readValue(response2.getBody(), KakaoProfileDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        UUID tempPassword = UUID.randomUUID(); // 회원가입을 위한 임시 패스워드 생성

        UserEntity userEntity = UserEntity.builder()
                                .username(kakaoProfileDto.getKakao_account().getEmail() + "_" + kakaoProfileDto.getId())
                                .userEmail(kakaoProfileDto.getKakao_account().getEmail())
                                .password(tempPassword.toString())
                                .name(kakaoProfileDto.properties.nickname)
                                .role(Role.USER)
                                .build();

        authService.회원가입(userEntity);

        return response2.getBody();
    }


}
