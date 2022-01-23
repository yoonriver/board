package com.project.board.dto;

import com.project.board.entity.CommentEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentDto {

    private Long id;
    @NotBlank
    private String content;
    private Long parentId;
    private UserEntity userEntity;
    private WriteEntity writeEntity;
    private int isDeleted;
    private CommentEntity parent;
    private List<CommentEntity> children;
    private LocalDateTime createDate;
}
