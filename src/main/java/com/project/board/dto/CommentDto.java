package com.project.board.dto;

import com.project.board.entity.CommentEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDto {

    @NotBlank
    private String content;

    private Long parentId;

}
