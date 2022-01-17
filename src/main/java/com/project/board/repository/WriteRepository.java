package com.project.board.repository;

import com.project.board.entity.WriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriteRepository extends JpaRepository<WriteEntity, Long> {

    // IgnoreCase : 대소문자 무시
    Page<WriteEntity> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(String content, String title, Pageable pageable);
    Page<WriteEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<WriteEntity> findByContentContainingIgnoreCase(String content, Pageable pageable);
    Page<WriteEntity> findByCategoryContainingIgnoreCase(String category, Pageable pageable);

}
