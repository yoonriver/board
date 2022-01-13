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
    public CommentEntity 댓글쓰기(Long userId, Long writeId, String content) {

        UserEntity userEntity = authRepository.findById(userId).get();
        WriteEntity writeEntity = writeRepository.findById(writeId).get();

        int ref = 0;
        if(commentRepository.isExists(writeId) != 0) {
            ref = commentRepository.refMax() + 1;
        }
        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUserEntity(userEntity);
        commentEntity.setWriteEntity(writeEntity);
        commentEntity.setContent(content);
        commentEntity.setLikes(0);
        commentEntity.setIsDeleted(1);
        commentEntity.setRef(ref);
        commentEntity.setRe_level(1);
        commentEntity.setRe_step(1);

        commentRepository.save(commentEntity);

        return commentEntity;
    }
    
    @Transactional
    public CommentEntity 대댓글쓰기(Long userId, Long writeId, String content, Long parentId) {

        UserEntity userEntity = authRepository.findById(userId).get();
        WriteEntity writeEntity = writeRepository.findById(writeId).get();
        CommentEntity parentComment = 댓글한개선택(parentId);
        int ref = parentComment.getRef();
        int re_level = parentComment.getRe_level();
        int re_step = parentComment.getRe_step();
        re_level증가(ref, re_level);

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setUserEntity(userEntity);
        commentEntity.setWriteEntity(writeEntity);
        commentEntity.setContent(content);
        commentEntity.setLikes(0);
        commentEntity.setIsDeleted(1);
        commentEntity.setRef(ref);
        commentEntity.setRe_level(re_level+1);
        commentEntity.setRe_step(re_step+1);

        commentRepository.save(commentEntity);

        return commentEntity;
    }

    @Transactional
    public void re_level증가(int ref, int re_level) {
        commentRepository.re_stepUp(ref, re_level);
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
    public void 댓글삭제(Long commentId, UserEntity userEntity) {

        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> {
            return new CustomValidationApiException("댓글이 없습니다.");
        });

        if(userEntity.getId() == commentEntity.getUserEntity().getId()) {
            commentRepository.deleteById(commentId);
        }else {
            throw new CustomUpdateValidationException("댓글을 삭제 할 권한이 없습니다.");
        }
    }
}
