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


    @ExceptionHandler(CustomStandardValidationException.class)
    public String customValidationException(CustomStandardValidationException e) {

        return Script.back(e.getMessage());
    }

    @ExceptionHandler(CustomAlertStandardValidationException.class)
    public String customValidationException(CustomAlertStandardValidationException e) {

        return Script.alert(e.getMessage());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {

        return new ResponseEntity<CMRespDto<?>>(
                new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),
                HttpStatus.BAD_REQUEST);
    }
}
