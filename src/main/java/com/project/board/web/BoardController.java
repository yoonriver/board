package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.WriteDto;
import com.project.board.entity.CommentEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.CommentRepository;
import com.project.board.repository.WriteRepository;
import com.project.board.service.CommentService;
import com.project.board.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.stream.events.Comment;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @GetMapping({"/", "/main"})
    public String main() {
        return "main";
    }

    @GetMapping("/board")
    public String board(Model model) {

        List<WriteEntity> writeList = writeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        model.addAttribute("writeList", writeList);

        return "board";
    }

    @GetMapping("/board/{writeId}")
    public String posts(Model model, @PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        WriteEntity writeEntity = writeService.글보기(writeId, principalDetails);
        model.addAttribute("writes", writeEntity);

        // 댓글 목록 불러오기
        List<CommentEntity> commentList = commentService.댓글불러오기(writeId);

        model.addAttribute("commentList", commentList);

        return "detail";
    }

    @GetMapping("/board/write")
    public String writeForm() {

        return "write";
    }

    @PostMapping("/board/write")
    public String write(@Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        WriteEntity writeEntity = writeService.글쓰기(writeDto.toEntity(), principalDetails.getUserEntity());

        return "redirect:/board/" + writeEntity.getId();
    }

    @GetMapping("/board/modify/{writeId}")
    public String modifyForm(@PathVariable Long writeId, Model model) {

        WriteEntity writeEntity = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationException("존재하지 않는 게시글 입니다.");
        });

        model.addAttribute("writes", writeEntity);

        return "modify";
    }



}
