package com.project.board.repository;

import com.project.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByUserEmail(String userEmail);

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByUsername(String username);
}
