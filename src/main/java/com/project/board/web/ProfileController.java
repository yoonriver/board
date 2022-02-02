package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.CommentEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.service.BoardService;
import com.project.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("profile/{id}/update")
    public String updateForm(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {

        if(principalDetails.getUserEntity().getId() != id) {
            throw new CustomStandardValidationException("권한이 없습니다.");
        }

        return "update";
    }



    @GetMapping("profile/{id}/pwUpdate")
    public String updatePw(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long id) {

        if(principalDetails.getUserEntity().getId() != id) {
            throw new CustomStandardValidationException("권한이 없습니다.");
        }

        return "pwUpdate";
    }

    @GetMapping("/profile/own-board")
    public String ownBoard(@RequestParam int page, @RequestParam(required = false) String option,
                        @RequestParam(required = false) String keyword, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        Page<WriteEntity> writeList = boardService.내글목록(page, option, keyword, principalDetails.getUserEntity().getId());

        model.addAttribute("writeList", writeList);
        model.addAttribute("pageNum", page);
        model.addAttribute("option", option);
        model.addAttribute("keyword", keyword);

        return "ownBoard";
    }

    @GetMapping("/profile/own-comment")
    public String ownComment(@RequestParam int page, @RequestParam(required = false) String option,
                             @RequestParam(required = false) String keyword, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        Page<CommentEntity> commentList = commentService.댓글페이지(page, option, keyword, principalDetails.getUserEntity().getId());

        model.addAttribute("commentList", commentList);
        model.addAttribute("pageNum", page);
        model.addAttribute("option", option);
        model.addAttribute("keyword", keyword);

        return "ownComment";
    }
}
