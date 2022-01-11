package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.WriteDto;
import com.project.board.entity.WriteEntity;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.handler.ex.CustomValidationException;
import com.project.board.repository.WriteRepository;
import com.project.board.service.WriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;

    @PutMapping("/api/board/modify/{writeId}")
    public CMRespDto<?> modify(@PathVariable Long writeId, @Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        WriteEntity writeEntity = writeRepository.findById(writeId).get();

        if(principalDetails.getUserEntity().getId() == writeEntity.getUserEntity().getId()) {
            writeService.글수정(writeId, writeDto.toEntity());
        }else {
            throw new CustomUpdateValidationException("수정 할 권한이 없습니다.");
        }

        return new CMRespDto<>(1, "글 수정 완료", null);
    }


    @DeleteMapping("/api/board/delete/{writeId}")
    public CMRespDto<?> delete(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        WriteEntity writeEntity = writeRepository.findById(writeId).get();

        if(principalDetails.getUserEntity().getId() == writeEntity.getUserEntity().getId()) {
            writeService.글삭제(writeId);
        }else {
            throw new CustomUpdateValidationException("삭제 할 권한이 없습니다.");
        }

        return new CMRespDto<>(1, "글 삭제 완료", null);

    }
}
