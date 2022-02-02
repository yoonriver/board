package com.project.board.repository;

import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByUserEmail(String userEmail);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserEmail(String userEmail);

    Page<UserEntity> findByUsernameContainingIgnoreCase(String title, Pageable pageable);

    Page<UserEntity> findByNameContainingIgnoreCase(String content, Pageable pageable);

    UserEntity findByNameContainingIgnoreCase(String content);
}
