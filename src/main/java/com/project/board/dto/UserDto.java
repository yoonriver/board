package com.project.board.dto;

import com.project.board.entity.UserEntity;
import com.project.board.role.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @Size(min = 2, max = 20) // 2자 이상 20자 이하
    private String userId;
    @NotBlank
    private String userPassword;
    @NotBlank
    private String userName;
    @NotBlank
    private String userGender;
    @NotBlank
    private String userEmail;

    public UserEntity toEntity() {
        return new UserEntity()
                    .builder()
                    .userId(userId)
                    .userPassword(userPassword)
                    .userName(userName)
                    .userGender(userGender)
                    .userEmail(userEmail)
                    .build();

    }
}
