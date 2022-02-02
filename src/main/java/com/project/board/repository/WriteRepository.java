package com.project.board.repository;

import com.project.board.entity.WriteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WriteRepository extends JpaRepository<WriteEntity, Long> {

    // IgnoreCase : 대소문자 무시
    Page<WriteEntity> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(String content, String title, Pageable pageable);
    Page<WriteEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<WriteEntity> findByContentContainingIgnoreCase(String content, Pageable pageable);
    Page<WriteEntity> findByCategoryContainingIgnoreCase(String category, Pageable pageable);
    Page<WriteEntity> findByUserEntity_NameContaining(String name, Pageable pageable);

    // 카테고리 검색
    @Query(nativeQuery = true, value = "select * from writes where (upper(content) like upper(concat('%', :keyword, '%')) or upper(title) like upper(concat('%', :keyword, '%'))) and category like %:category% ")
    Page<WriteEntity> findByContentContainingIgnoreCaseAndCategoryContainingOrTitleContainingIgnoreCaseAndCategoryContaining(@Param("category") String category, @Param("keyword") String keyword, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from writes where upper(title) like upper(concat('%', :keyword, '%')) and category like %:category% ")
    Page<WriteEntity> findByTitleContainingIgnoreCaseAndCategoryContaining(@Param("category") String category, @Param("keyword") String keyword, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from writes where upper(content) like upper(concat('%', :keyword, '%')) and category like %:category% ")
    Page<WriteEntity> findByContentContainingIgnoreCaseAndCategoryContaining(@Param("category") String category, @Param("keyword") String keyword, Pageable pageable);

    // 공지글 목록
    List<WriteEntity> findByCategoryContainingOrderByCreateDateAsc(String category);

    // 내 글 목록
    @Query(nativeQuery = true, value = "select * from writes where user_id like :userId")
    Page<WriteEntity> findByUserIdContaining(@Param("userId") Long userId, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from writes where (upper(content) like upper(concat('%', :keyword, '%')) or upper(title) like upper(concat('%', :keyword, '%'))) and user_id like :userId")
    Page<WriteEntity> findByUserContainingIgnoreCaseOrTitleContainingIgnoreCaseAndUserIdContaining(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from writes where upper(content) like upper(concat('%', :keyword, '%')) and user_id like :userId")
    Page<WriteEntity> findByContentContainingIgnoreCaseAndUserIdContaining(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);
    @Query(nativeQuery = true, value = "select * from writes where upper(title) like upper(concat('%', :keyword, '%')) and user_id like :userId")
    Page<WriteEntity> findByTitleContainingIgnoreCaseAndUserIdContaining(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);
}
