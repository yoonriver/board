package com.project.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "WRITES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WriteEntity {

    @Id
    @GeneratedValue(generator = "POST_SEQ")
    private Long id;
    private String title;
    private String category;
    private String content;
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "writeEntity")
    @JsonIgnoreProperties({"writeEntity"})
    private List<CommentEntity> comments = new ArrayList<>();

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
