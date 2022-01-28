package com.project.board.web;

import com.project.board.config.auth.PrincipalDetails;
import com.project.board.entity.UserEntity;
import com.project.board.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user/list")
    public String userList(@RequestParam int page, @RequestParam(required = false) String option,
                           @RequestParam(required = false) String keyword, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        Page<UserEntity> userList = adminService.회원목록(page, option, keyword, principalDetails.getUserEntity());

        model.addAttribute("userList", userList);
        model.addAttribute("pageNum", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);

        return "user";
    }

    @GetMapping("/user/info/{userId}")
    public String userInfo(@RequestParam int page, @RequestParam(required = false) String option,
                           @RequestParam(required = false) String keyword, @PathVariable Long userId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        UserEntity userEntity = adminService.회원정보(userId, principalDetails.getUserEntity());
        model.addAttribute("user", userEntity);
        model.addAttribute("pageNum", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);

        return "userInfo";
    }
}
