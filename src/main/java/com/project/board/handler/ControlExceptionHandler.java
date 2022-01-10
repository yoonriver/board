package com.project.board.handler;

import com.project.board.dto.CMRespDto;
import com.project.board.handler.ex.CustomPwUpdateValidationException;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.utill.Script;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(CustomUpdateValidationException.class)
    public String updateValidationException(CustomUpdateValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CustomPwUpdateValidationException.class)
    public String pwUpdateValidationException(CustomPwUpdateValidationException e) {
        return e.getMessage();
    }


    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<CMRespDto<?>>(
                new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }
}
