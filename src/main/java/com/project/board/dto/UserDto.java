package com.project.board.dto;

import com.project.board.entity.LikesEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.role.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UserDto {

    private Long id;
    @Size(min = 2, max = 20) // 2자 이상 20자 이하
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String userEmail;
    private List<WriteEntity> writes;
    private List<LikesEntity> likesEntities;
    private Role role;


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
