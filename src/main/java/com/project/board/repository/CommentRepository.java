package com.project.board.repository;

import com.project.board.entity.CommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "SELECT * FROM COMMENT WHERE WRITE_ID = :writeId AND PARENT_ID IS NULL", nativeQuery = true)
    List<CommentEntity> findAllByWriteId(Long writeId);

}
