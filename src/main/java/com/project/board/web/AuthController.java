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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String kakaoCallback(String code) {

        authService.카카오로그인(code);

        return "redirect:/main";
    }


}
