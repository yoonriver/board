package com.project.board.service;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WriteService {

    private final WriteRepository writeRepository;

    @Transactional
    public WriteEntity 글쓰기(WriteEntity writeEntity, UserEntity userEntity) {

        writeEntity.setCount(0);
        writeEntity.setUserEntity(userEntity);

        writeRepository.save(writeEntity);
        WriteEntity findWrites = writeRepository.findById(writeEntity.getId()).get();

        return findWrites;
    }

    @Transactional
    public void 글수정(Long writeId, WriteEntity writeEntity) {

        WriteEntity findWrites = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationApiException("게시글이 없습니다.");
        });

        findWrites.setTitle(writeEntity.getTitle());
        findWrites.setCategory(writeEntity.getCategory());
        findWrites.setContent(writeEntity.getContent());

    }

    @Transactional
    public WriteEntity 글보기(Long writeId, PrincipalDetails principalDetails) {

        WriteEntity writeEntity = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationException("존재하지 않는 게시글 입니다.");
        });

        // 글 쓴 본인이 아니면 조회수+1
        if(writeEntity.getUserEntity().getId() != principalDetails.getUserEntity().getId()) {
            writeEntity.setCount(writeEntity.getCount() + 1);
        }

        return writeEntity;
    }
    
    @Transactional
    public void 글삭제(Long writeId) {
        writeRepository.deleteById(writeId);
    }
}
