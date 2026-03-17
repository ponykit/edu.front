package com.edu.front.interceptor;


import com.edu.front.model.common.Category;
import com.edu.front.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;


@Slf4j
public class MenuListInjectInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommonService commonService;

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("Request URI ===> " + request.getRequestURI());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        if (modelAndView != null){
            List<Category> categories = commonService.selectCategoryList();
            request.setAttribute("categories", categories);

            log.debug("Controller Mapping Uri : {} -> View : {}", request.getRequestURI(), modelAndView.getViewName());
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
