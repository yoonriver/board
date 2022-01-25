package com.project.board.dto;

import com.project.board.entity.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

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
    private List<MultipartFile> fileList;
    private List<FileEntity> files;
    private List<String> deleteImageNames;
    private LocalDateTime createDate;


    public WriteEntity toEntity(UserEntity userEntity, int count) {
        return new WriteEntity().builder()
                .category(category)
                .title(title)
                .content(content)
                .userEntity(userEntity)
                .count(count)
                .build();
    }

    public WriteEntity toEntity() {
        return new WriteEntity().builder()
                .category(category)
                .title(title)
                .content(content)
                .build();
    }
}
