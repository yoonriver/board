package com.project.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "COMMENT_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITE_ID")
    @JsonIgnoreProperties("CommentEntity")
    private WriteEntity writeEntity;

    private String content;
    private int isDeleted; // 0이면 삭제, 1이면 삭제되지 않음

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    @JsonIgnoreProperties("parent")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("children")
    private List<CommentEntity> children;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
