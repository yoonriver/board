package com.project.board.repository;

import com.project.board.entity.WriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteRepository extends JpaRepository<WriteEntity, Long> {
}
