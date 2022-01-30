package com.project.board.web.api;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.dto.CMRespDto;
import com.project.board.handler.ex.CustomStandardValidationException;
import com.project.board.handler.ex.CustomValidationApiException;
import com.project.board.role.Role;
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

    @DeleteMapping("/api/user/delete/{userId}")
    public ResponseEntity<?> userDelete(@PathVariable Long userId, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(principalDetails.getUserEntity().getRole() != Role.ADMIN) {
            throw new CustomValidationApiException("관리자가 아니므로 접근할 수 없습니다.");
        }

        if(principalDetails.getUserEntity().getId() == userId) {
            throw new CustomValidationApiException("로그인 한 계정은 삭제 할 수 없습니다.");
        }


        adminService.회원삭제(userId, principalDetails.getUserEntity());

        return new ResponseEntity<>(new CMRespDto<>(1, "삭제 성공", null), HttpStatus.OK);
    }

}
