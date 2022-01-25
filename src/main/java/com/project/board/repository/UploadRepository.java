package com.project.board.repository;

import com.project.board.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UploadRepository extends JpaRepository<FileEntity, Long> {

    FileEntity findByImageFileName(String imageFileName);

}