package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CommentDto;
import com.project.board.dto.WriteDto;
import com.project.board.entity.CommentEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.WriteRepository;
import com.project.board.service.BoardService;
import com.project.board.service.CommentService;
import com.project.board.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;
    private final BoardService boardService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @GetMapping({"/", "/main"})
    public String main() {
        return "main";
    }

    @GetMapping("/board/list")
    public String board(@RequestParam int page, @RequestParam(required = false) String option,
                        @RequestParam(required = false) String keyword, @RequestParam(required = false) String category, Model model) {

        Page<WriteEntity> writeList = boardService.글목록(page, option, keyword, category);

        model.addAttribute("writeList", writeList);
        model.addAttribute("pageNum", page);
        model.addAttribute("option", option);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);

        return "board";
    }

    @GetMapping("/board/{writeId}")
    public String detail(Model model, @PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails,
                         @RequestParam int page, @RequestParam(required = false) String option,
                         @RequestParam(required = false) String keyword, @RequestParam(required = false) String category) {

        WriteEntity writeEntity = writeService.글보기(writeId, principalDetails);
        WriteDto writeDto = modelMapper.map(writeEntity, WriteDto.class);
        model.addAttribute("writes", writeDto);

        // 댓글 목록 불러오기
        List<CommentEntity> commentList = commentService.댓글불러오기(writeId);
        List<CommentDto> resultList = commentList.stream().map(post -> modelMapper.map(post, CommentDto.class)).collect(Collectors.toList());

        model.addAttribute("commentList", commentList);
        model.addAttribute("pageNum", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        model.addAttribute("category", category);

        return "detail";
    }

    @GetMapping("/board/write")
    public String writeForm(Model model, @RequestParam int page) {
        model.addAttribute("pageNum", page);

        return "write";
    }

    @PostMapping("/board/write")
    public String write(@Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam int page) {

        WriteEntity writeEntity = writeService.글쓰기(writeDto, principalDetails.getUserEntity());

        return "redirect:/board/" + writeEntity.getId() + "?page=" + page;
    }

    @GetMapping("/board/modify/{writeId}")
    public String modifyForm(@PathVariable Long writeId, Model model, @RequestParam int page) {

        WriteEntity writeEntity = writeRepository.findById(writeId).orElseThrow(() -> {
            return new CustomValidationException("존재하지 않는 게시글 입니다.");
        });

        WriteDto writeDto = modelMapper.map(writeEntity, WriteDto.class);

        model.addAttribute("writes", writeDto);
        model.addAttribute("pageNum", page);

        return "modify";
    }

    @PostMapping("/board/modify/{writeId}")
    public String modify(@PathVariable Long writeId, @Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails, @RequestParam int page) {
        WriteEntity writeEntity = writeRepository.findById(writeId).get();

        writeService.글수정(writeId, writeDto, principalDetails.getUserEntity());

        return "redirect:/board/" + writeEntity.getId() + "?page=" + page;
    }

}
