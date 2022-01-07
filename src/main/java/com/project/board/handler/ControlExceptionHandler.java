package com.project.board.handler;

import com.project.board.handler.ex.CustomValidationException;
import com.project.board.utill.Script;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {

        return Script.back(e.getErrorMap().toString());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String usernameException(UsernameNotFoundException e) {
        System.out.println("예외 실행됨");
        return Script.alert(e.getMessage());
    }

}
