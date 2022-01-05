package com.project.board.dto;

public class CMRespDto<T> {

    private int code; // 성공이면 1, 실패면 -1
    private String message;
    private T data;
}
