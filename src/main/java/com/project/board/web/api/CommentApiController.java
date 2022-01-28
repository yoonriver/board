package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.CommentDto;
import com.project.board.entity.WriteEntity;
import com.project.board.repository.CommentRepository;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/api/comment/{writeId}")
    public ResponseEntity<?> comment(@Valid CommentDto commentDto, BindingResult bindingResult, @PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.댓글쓰기(principalDetails.getUserEntity().getId(), writeId, commentDto.getContent(), commentDto.getParentId());

        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 쓰기 성공", null), HttpStatus.CREATED);
    }

    @PutMapping("/api/comment/modify/{commentId}")
    public ResponseEntity<?> modify(@PathVariable Long commentId, @Valid CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentService.댓글수정(commentId, commentDto.getContent(), principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "글 수정 완료", null), HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/delete/{commentId}")
    public ResponseEntity<?> delete(@PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        int size = commentRepository.getById(commentId).getChildren().size();

        commentService.댓글삭제(commentId, principalDetails.getUserEntity(),size);

        return new ResponseEntity<>(new CMRespDto<>(1, "글 삭제 완료", null),HttpStatus.OK);

    }


}
