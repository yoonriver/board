package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.entity.WriteEntity;
import com.project.board.repository.WriteRepository;
import com.project.board.service.WriteService;
import com.project.board.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final WriteService writeService;
    private final WriteRepository writeRepository;
    private final LikesService likesService;

    @Value("${file.path}")
    private String uploadFolder;

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
