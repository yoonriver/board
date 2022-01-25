package com.project.board.handler;

import com.project.board.dto.CMRespDto;
import com.project.board.handler.ex.*;
import com.project.board.utill.Script;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControlExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {

        return Script.alert(e.getErrorMap().toString());
    }

    @ExceptionHandler(CustomStandardValidationException.class)
    public String customValidationException(CustomStandardValidationException e) {

        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomAlertStandardValidationException.class)
    public String customValidationException(CustomAlertStandardValidationException e) {

        return Script.alert(e.getMessage());
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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String uploadValidationException(MaxUploadSizeExceededException e) {

        return Script.back("용량이 10mb를 넘을 수 없습니다.");
    }
}
