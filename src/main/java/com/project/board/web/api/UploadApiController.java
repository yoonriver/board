package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.UserUpdateDto;
import com.project.board.entity.UserEntity;
import com.project.board.handler.ex.CustomUpdateValidationException;
import com.project.board.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UploadApiController {

    private final UploadService uploadService;

    @DeleteMapping("api/upload/{fileId}")
    public ResponseEntity<?> update(@PathVariable Long fileId,
                                    @AuthenticationPrincipal PrincipalDetails principalDetails) {

        uploadService.첨부파일삭제(fileId, principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "첨부파일 삭제 완료", null), HttpStatus.OK);

    }

}
