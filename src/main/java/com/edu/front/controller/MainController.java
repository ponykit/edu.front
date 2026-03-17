package com.edu.front.controller;

import com.edu.front.model.common.Category;
import com.edu.front.service.CommonService;
import com.edu.front.service.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    FrontService frontService;

    @RequestMapping("/")
    public String main(Model model, @RequestParam HashMap<String, Object> params) {
        params.put("Current", 1);
        params.put("RowCount", 3);
        params.put("SortBy", "regDt");
        params.put("SortDirection", "ASC");

        //카테고리별
        List<HashMap> cateTop = frontService.selectMainList(params);
        //자격증과정
        List<HashMap> license = frontService.selectMainList(params);
        //신규강의
        List<HashMap> newCours = frontService.selectMainList(params);
         // 게시판조회
        params.put("SortBy", "RegDate");
        params.put("SortDirection", "DESC");
        List<HashMap> boadList = frontService.selectBBSList(params);
        model.addAttribute("cateTop", cateTop);
        model.addAttribute("license", license);
        model.addAttribute("newCours", newCours);
        model.addAttribute("boadList", boadList);

        return "pages/index";
    }
}
