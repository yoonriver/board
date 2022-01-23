package com.project.board.dto;

import com.project.board.entity.CommentEntity;
import com.project.board.entity.LikesEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class WriteDto {

    private Long id;
    @NotNull
    private String category;
    @NotNull
    @Length(max = 20)
    private String title;
    @NotNull
    private String content;
    private int count;
    private UserEntity userEntity;
    private List<CommentEntity> comments;
    private List<LikesEntity> likes;
    private int isLikes;
    private LocalDateTime createDate;

    public WriteEntity toEntity() {
        return new WriteEntity().builder()
                .category(category)
                .title(title)
                .content(content)
                .build();
    }
}
