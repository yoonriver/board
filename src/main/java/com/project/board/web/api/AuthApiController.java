package com.project.board.web.api;

import com.project.board.dto.CMRespDto;
import com.project.board.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @GetMapping("/api/auth/idCheck/{username}")
    public ResponseEntity<?> idCheck(@PathVariable String username) {

        Boolean isExists = authService.아이디확인(username);

        return new ResponseEntity<>(new CMRespDto<>(1, "중복 확인", isExists), HttpStatus.OK);
    }

    @GetMapping("/api/auth/emailCheck/{userEmail}")
    public ResponseEntity<?> emailCheck(@PathVariable String userEmail) {

        Boolean isExists = authService.이메일확인(userEmail);

        return new ResponseEntity<>(new CMRespDto<>(1, "중복 확인", isExists), HttpStatus.OK);
    }
}
