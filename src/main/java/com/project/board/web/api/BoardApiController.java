package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.CommentDto;
import com.project.board.dto.WriteDto;
import com.project.board.entity.CommentEntity;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.WriteRepository;
import com.project.board.service.CommentService;
import com.project.board.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.stream.events.Comment;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;


    @PutMapping("/api/board/modify/{writeId}")
    public CMRespDto<?> modify(@PathVariable Long writeId, @Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        WriteEntity writeEntity = writeRepository.findById(writeId).get();


        writeService.글수정(writeId, writeDto.toEntity(), principalDetails.getUserEntity());


        return new CMRespDto<>(1, "글 수정 완료", null);
    }


    @DeleteMapping("/api/board/delete/{writeId}")
    public CMRespDto<?> delete(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        writeService.글삭제(writeId, principalDetails.getUserEntity());

        return new CMRespDto<>(1, "글 삭제 완료", null);

    }




}
