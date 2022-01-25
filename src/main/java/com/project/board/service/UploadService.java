package com.project.board.service;

import com.project.board.entity.FileEntity;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomAlertStandardValidationException;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadService {

    @Value("${file.path}")
    private String uploadFolder;

    private final UploadRepository uploadRepository;
    
    @Transactional
    public void 첨부파일삭제(Long fileId, UserEntity userEntity) {

        FileEntity findFile = uploadRepository.findById(fileId).orElseThrow(() -> {
            throw new CustomStandardValidationException("해당 첨부파일이 없습니다.");
        });

        if(findFile.getWriteEntity().getUserEntity().getId() != userEntity.getId()) {
            throw new CustomAlertStandardValidationException("권한이 없습니다.");
        }

        String path = uploadFolder + findFile.getImageFileName(); // 삭제 할 파일의 경로

        File file = new File(path);

        if(file.exists() == true){
            file.delete();
            uploadRepository.deleteById(fileId);
        }
    }
    
    
}
