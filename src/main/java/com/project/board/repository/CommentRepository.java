package com.project.board.repository;

import com.project.board.entity.CommentEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "SELECT * FROM COMMENT ORDER BY REF ASC, RE_LEVEL ASC", nativeQuery = true)
    List<CommentEntity> findAllByWriteId(Long writeId);

    @Query(value = "SELECT MAX(REF) FROM COMMENT", nativeQuery = true)
    Integer refMax();

    @Query(value = "SELECT COUNT(*) FROM COMMENT WHERE WRITE_ID = :writeId", nativeQuery = true)
    int isExists(Long writeId);

    @Query(value = "UPDATE COMMENT SET RE_LEVEL=RE_LEVEL+1 WHERE REF = :ref AND RE_LEVEL > :re_level", nativeQuery = true)
    void re_stepUp(int ref, int re_level);


}
