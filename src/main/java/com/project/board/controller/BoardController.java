package com.project.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {


    @GetMapping({"/board/list", "/"})
    public String list(Model model) {

        return "/board/list";
    }
}
