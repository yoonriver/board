package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    @DeleteMapping("/api/user/delete/{writeId}")
    public ResponseEntity<?> userDelete(@PathVariable Long writeId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        adminService.회원삭제(writeId, principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "삭제 성공", null), HttpStatus.OK);
    }

}
