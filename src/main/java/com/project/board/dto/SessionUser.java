package com.project.board.dto;

import com.project.board.entity.UserEntity;
import lombok.Getter;

import java.io.Serializable;

// 직렬화 기능을 가진 userEntity 클래스
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String userEmail;

    public SessionUser(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.userEmail = userEntity.getUserEmail();
    }

}
