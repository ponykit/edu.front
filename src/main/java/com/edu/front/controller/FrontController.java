package com.edu.front.controller;

import com.edu.front.model.common.Category;
import com.edu.front.model.common.CommonCode;
import com.edu.front.model.course.Course;
import com.edu.front.service.CommonService;
import com.edu.front.service.FrontService;
import com.edu.front.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
public class FrontController {
    @Autowired
    CommonService commonService;

    @Autowired
    FrontService frontService;

    @RequestMapping("courses/course-list")
    public String coursesList(Model model, @RequestParam HashMap<String, Object> params) {
        params.put("Current", 1);
        params.put("RowCount", 3);
        params.put("SortBy", "regDt");
        params.put("SortDirection", "ASC");

        List<Course>  courseList = frontService.selectCourseList(params);
        List<CommonCode> codeList = commonService.selectCommonCodeList();

        model.addAttribute("courseList", courseList);
        model.addAttribute("tags", codeList);

      return "pages/courses/course-list";
    }

    @RequestMapping("courses/course-detail")
    public String coursesDetail(Model model, @RequestParam HashMap<String, Object> params) {

        if(params.get("courseSeq") == null)
            params.put("courseSeq" , "1");

        Course courseDetail =  frontService.selectCourseDetail(params);

        model.addAttribute("courseDetail", courseDetail);

        return "pages/courses/course-detail";
    }

    @RequestMapping("order/cart-list")
    public String cartList(Model model, @RequestParam HashMap<String, Object> params) {

        return "pages/order/cart-list";
    }

}
