package com.project.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 20, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userGender;
    @Column(nullable = false, unique = true)
    private String userEmail;

    private String role;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
