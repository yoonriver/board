package com.project.board.dto;

import com.project.board.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserPwUpdateDto {

    @NotBlank
    private String password;

    @NotBlank
    private String mod_password1;

    @NotBlank
    private String mod_password2;

    public UserEntity toEntity() {

        return new UserEntity().builder()
                                .password(password)
                                .build();
    }

}
