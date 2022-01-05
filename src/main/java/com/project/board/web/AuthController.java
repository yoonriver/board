package com.project.board.web;

import com.project.board.dto.UserDto;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

        return "/";
    }

    @GetMapping("/auth/join")
    public String joinForm() {

        return "join";
    }

    @PostMapping("/auth/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult) {

        UserEntity userEntity = userDto.toEntity();
        authService.회원가입(userEntity);

        return "/main";

    }
}
