package com.project.board.repository;

import com.project.board.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "SELECT * FROM COMMENT WHERE WRITE_ID = :writeId AND PARENT_ID IS NULL", nativeQuery = true)
    List<CommentEntity> findAllByWriteId(Long writeId);

    Page<CommentEntity> findAllByUserEntity_Id(Long id, Pageable pageable);

}
