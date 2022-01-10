package com.project.board.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("profile/{id}/update")
    public String updateForm() {
        return "update";
    }



    @GetMapping("profile/{id}/pwUpdate")
    public String updatePw() {
        return "pwUpdate";
    }
}
