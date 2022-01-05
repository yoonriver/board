package com.project.board.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"/", "/main"})
    public String main() {
        return "main";
    }

    @GetMapping("/board")
    public String board() {

        return "bbs";
    }

    @GetMapping("/board/write")
    public String write() {

        return "write";
    }
}
