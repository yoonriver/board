package com.project.board.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IdEmailDto {

    @NotBlank
    private String userEmail;
    @NotBlank
    private String username;
}
