package com.project.board.service;

import com.project.board.entity.LikesEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.repository.LikesRepository;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final WriteRepository writeRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public void 추천(Long writeId, UserEntity userEntity) {

        WriteEntity writeEntity = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationApiException("게시글이 없습니다.");
        });

        LikesEntity likesEntity = new LikesEntity();
        likesEntity.setUserEntity(userEntity);
        likesEntity.setWriteEntity(writeEntity);

        likesRepository.save(likesEntity);

    }

    @Transactional
    public void 안추천(Long writeId, Long userId) {

        likesRepository.deleteByWriteIdAndUserId(writeId, userId);
    }
}
