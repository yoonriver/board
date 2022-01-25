package com.project.board.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "FILES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "FILES_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WRITE_ID")
    private WriteEntity writeEntity;

    String imageFileName;

    private LocalDateTime createDate;

    @PrePersist
    private void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
