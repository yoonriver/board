package com.project.board.repository;

import com.project.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdateRepository extends JpaRepository<UserEntity, Long> {


}
