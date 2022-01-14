package com.project.board.service;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.CommentEntity;
import com.project.board.entity.UserEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.AuthRepository;
import com.project.board.repository.CommentRepository;
import com.project.board.repository.WriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    final private CommentRepository commentRepository;
    final private AuthRepository authRepository;
    final private WriteRepository writeRepository;

    @Transactional
    public CommentEntity 댓글쓰기(Long userId, Long writeId, String content, Long parentId) {

        UserEntity userEntity = authRepository.findById(userId).get();
        WriteEntity writeEntity = writeRepository.findById(writeId).get();
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUserEntity(userEntity);
        commentEntity.setWriteEntity(writeEntity);
        commentEntity.setContent(content);
        commentEntity.setLikes(0);
        commentEntity.setIsDeleted(1);

        if(parentId != null) {
            CommentEntity parent = 댓글한개선택(parentId);
            commentEntity.setParent(parent);
        }

        commentRepository.save(commentEntity);

        return commentEntity;
    }
    

    @Transactional
    public List<CommentEntity> 댓글불러오기(Long writeId) {

        List<CommentEntity> commentList =  commentRepository.findAllByWriteId(writeId);

        return commentList;
    }

    @Transactional
    public CommentEntity 댓글한개선택(Long commentId) {

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            return new CustomValidationApiException("댓글이 없습니다.");
        });

        return commentEntity;
    }

    @Transactional
    public void 댓글수정(Long commentId, String content, UserEntity userEntity) {

        CommentEntity commentEntity = commentRepository.findById(commentId).get();

        if(userEntity.getId() == commentEntity.getUserEntity().getId()) {
            commentEntity.setContent(content);

        }else {
            throw new CustomUpdateValidationException("수정 할 권한이 없습니다.");
        }
    }

    @Transactional
    public void 댓글삭제(Long commentId, UserEntity userEntity, int size) {

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            throw new CustomValidationApiException("댓글이 없습니다.");
        });

        if(size != 0) {
            commentEntity.setIsDeleted(0);
            commentEntity.setContent("(삭제 된 댓글입니다.)");
        }else{
            if (userEntity.getId() == commentEntity.getUserEntity().getId()) {
                commentRepository.deleteById(commentId);
            } else {
                throw new CustomUpdateValidationException("댓글을 삭제 할 권한이 없습니다.");
            }
        }

    }
}
