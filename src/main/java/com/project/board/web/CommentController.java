package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.CommentEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.CommentRepository;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/modify/{commentId}")
    public String commentModify(@PathVariable Long commentId, Model model) {

        CommentEntity commentEntity = commentService.댓글한개선택(commentId);

        model.addAttribute("comment",commentEntity);

        return "commentModify";
    }

    @GetMapping("/comment/reply/{commentId}")
    public String commentReplyForm(@PathVariable Long commentId, Model model) {
        CommentEntity commentEntity = commentService.댓글한개선택(commentId);

        model.addAttribute("comment", commentEntity);

        return "commentReply";


    }



}
