package com.edu.front.service;

import com.edu.front.dao.CommonDao;
import com.edu.front.model.common.Category;
import com.edu.front.model.common.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CommonService {
    @Autowired
    CommonDao commonDao;

    /**
     * 카테고리 조회
     *
     * @return
     */
    public List<Category> selectCategoryList() {
        return commonDao.selectCategoryList();
    }

    /**
     * 공통코드 조회
     *
     * @return
     */
    public List<CommonCode> selectCommonCodeList() {
        return commonDao.selectCommonCodeList();
    }


}