package com.project.board.dto;

import com.project.board.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {

    @NotBlank
    private String password;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String name;

    public UserEntity toEntity() {

        return new UserEntity().builder()
                                .password(password)
                                .userEmail(userEmail)
                                .name(name)
                                .build();
    }

}
