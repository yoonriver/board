package com.project.board.repository;

import com.project.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUserId(String UserId);

    Boolean existsByUserEmail(String UserEmail);
}
