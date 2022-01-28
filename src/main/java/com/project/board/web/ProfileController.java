package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.handler.ex.CustomStandardValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProfileController {

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
}
