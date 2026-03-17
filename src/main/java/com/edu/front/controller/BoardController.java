package com.edu.front.controller;

import com.edu.front.service.FrontService;
import com.edu.front.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class BoardController {

    @Autowired
    FrontService frontService;

    @RequestMapping("/board/board-list/{boardType}")
    public String boardList(@PathVariable("boardType") String boardType, Model model, @RequestParam HashMap<String, Object> params) {
        params.put("RowCount", 10);

        if(params.get("Current") == null) {
            params.put("Current", 1);
        }

        //페이징 세팅
        int page = Integer.parseInt(params.get("Current").toString());
        params.put("boardType", boardType);
        params.put("Current", page);
        params.put("SortBy", "BoNum");
        List<HashMap> rslt = frontService.selectBBSList(params);


        int totalListCnt = rslt.size() > 0 ? (int) Long.parseLong(rslt.get(0).get("BoRowCnt").toString()) : 0;
        Pagination pagination = new Pagination(totalListCnt, page, 10);

        model.addAttribute("pagination", pagination);
        model.addAttribute("itemLst", rslt);

        return "pages/board/board-list";
    }

    @RequestMapping("/board/board-detail")
    public String boardDetail(Model model, @RequestParam("BoNum") String BoNum) {
        HashMap<String, Object> params = new HashMap<>();
        String mode = "view";
        params.put("BoNum", BoNum);

        //게시글 상세
        HashMap  rslt = frontService.selectBBSDetail(params);

        model.addAttribute("detail", rslt);
        model.addAttribute("mode", mode);
        return "pages/board/board-detail";
    }

    @RequestMapping("/board/board-comment/{boardType}")
    public String boardCommentList(@PathVariable("boardType") String boardType, Model model, @RequestBody HashMap<String, Object> params) {
        params.put("RowCount", 5);

        if(params.get("Current") == null) {
            params.put("Current", 1);
        }

        //페이징 세팅
        int page = Integer.parseInt(params.get("Current").toString());
        params.put("boardType", boardType);
        params.put("Current", page);
        params.put("SortBy", "BoNum");
        List<HashMap> rslt = frontService.selectBBSList(params);


        int totalListCnt = rslt.size() > 0 ? (int) Long.parseLong(rslt.get(0).get("BoRowCnt").toString()) : 0;
        Pagination pagination = new Pagination(totalListCnt, page, 5);

        model.addAttribute("pagination", pagination);
        model.addAttribute("itemLst", rslt);

        return "fragments/board-comment::boardComment";
    }

    @RequestMapping("/board/board-comment/create")
    public String boardCommentCreate(Model model, @RequestBody HashMap<String, Object> params) {
        params.put("Subject", "댓글");
        int rslt =  frontService.insertBBS(params);
        model.addAttribute("rslt", rslt);
        return "fragments/board-comment::boardCommentRslt";
    }

}
