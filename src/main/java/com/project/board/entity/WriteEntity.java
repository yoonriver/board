package com.project.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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
    @JsonIgnoreProperties({"writeEntity"})
    private UserEntity userEntity;

    @OneToMany(mappedBy = "writeEntity",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonIgnoreProperties({"writeEntity"})
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "writeEntity",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonIgnoreProperties({"writeEntity"})
    private List<LikesEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "writeEntity",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @JsonIgnoreProperties({"writeEntity"})
    private List<FileEntity> files = new ArrayList<>();

    @Transient
    private int isLikes; // 1이면 추천을 누른 상태

    @Column(nullable = false)
    private LocalDateTime createDate;

    @PrePersist
    private void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "WriteEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", isLikes=" + isLikes +
                ", createDate=" + createDate +
                '}';
    }
}
