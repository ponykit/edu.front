package com.edu.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

    @RequestMapping("/mypage/myinfo")
    public String myinfo(Model model) {

        return "pages/mypage/myinfo";
    }
}
