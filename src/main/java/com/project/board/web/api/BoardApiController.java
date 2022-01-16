package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.dto.WriteDto;
import com.project.board.entity.WriteEntity;
import com.project.board.repository.WriteRepository;
import com.project.board.service.WriteService;
import com.project.board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;
    private final LikesService likesService;


    @PutMapping("/api/board/modify/{writeId}")
    public ResponseEntity<?> modify(@PathVariable Long writeId, @Valid WriteDto writeDto, BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        WriteEntity writeEntity = writeRepository.findById(writeId).get();

        writeService.글수정(writeId, writeDto.toEntity(), principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "글 수정 완료", null), HttpStatus.OK);
    }


    @DeleteMapping("/api/board/delete/{writeId}")
    public ResponseEntity<?> delete(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        writeService.글삭제(writeId, principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "글 삭제 완료", null), HttpStatus.OK);

    }

    @PostMapping("api/board/likes/{writeId}")
    public ResponseEntity<?> likes(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.추천(writeId, principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "추천 완료", null), HttpStatus.OK);
    }

    @DeleteMapping("api/board/likes/{writeId}")
    public ResponseEntity<?> unLikes(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.안추천(writeId, principalDetails.getUserEntity().getId());

        return new ResponseEntity<>(new CMRespDto<>(1, "안추천 완료", null), HttpStatus.OK);
    }

}
