package com.edu.front.dao;

import com.edu.front.model.common.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonDao {

    //공통코드
    List<CommonCode> selectCommonCodeList();

    //카테고리
    List<Category> selectCategoryList();

}