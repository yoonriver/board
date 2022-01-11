package com.project.board.dto;

import com.project.board.entity.WriteEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class WriteDto {

    @NotNull
    public String category;

    @NotNull
    @Length(max = 20)
    public String title;

    @NotNull
    public String content;

    public WriteEntity toEntity() {
        return new WriteEntity().builder()
                .category(category)
                .title(title)
                .content(content)
                .build();
    }
}
