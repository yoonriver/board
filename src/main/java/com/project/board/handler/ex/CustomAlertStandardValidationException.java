package com.project.board.handler.ex;

import java.util.Map;

public class CustomAlertStandardValidationException extends RuntimeException{

//    private static final longserialVersionUID = 1L;

    private Map<String, String> errorMap;

    public CustomAlertStandardValidationException(String message) {
        super(message);
    }

    public CustomAlertStandardValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }


}
