package com.project.board.dto;

import com.project.board.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @Size(min = 2, max = 20) // 2자 이상 20자 이하
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String userEmail;

    public UserEntity toEntity() {
        return new UserEntity()
                    .builder()
                    .username(username)
                    .password(password)
                    .name(name)
                    .userEmail(userEmail)
                    .build();

    }
}
