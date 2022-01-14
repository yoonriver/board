package com.project.board.repository;

import com.project.board.entity.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<LikesEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM likes WHERE WRITE_ID = :writeId AND USER_ID = :userId", nativeQuery = true)
    void deleteByWriteIdAndUserId(Long writeId, Long userId);
}