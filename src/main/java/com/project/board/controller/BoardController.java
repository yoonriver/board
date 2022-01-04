package com.project.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("/board")
    public String board() {

        return "bbs";
    }

    @GetMapping("/board/write")
    public String write() {

        return "write";
    }
}
