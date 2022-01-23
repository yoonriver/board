package com.project.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.board.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USER_SEQ")
    private Long id;

    @Column(unique = true, length = 100, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String userEmail;

    @OneToMany(mappedBy = "userEntity")
    //@JsonIgnoreProperties({"userEntity"})
    @JsonIgnore
    private List<WriteEntity> writes = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity")
//    @JsonIgnoreProperties({"userEntity"})
    @JsonIgnore
    private List<LikesEntity> likesEntities = new ArrayList<>();

    private Role role;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
