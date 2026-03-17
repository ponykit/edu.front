package com.edu.front.dao;

import com.edu.front.model.course.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface FrontDao {


    List<HashMap> selectMainList(HashMap<String, Object> params);

    /**
     * 강의리스트조회
     *
     * @param params
     * @return
     */
    List<Course> selectCourseList(HashMap<String, Object> params);

    /**
     * 강의정보조회
     *
     * @param params
     * @return
     */
    Course selectCourseInfo(HashMap<String, Object> params);

    /**
     * 강의정보조회 상세조회
     *
     * @param params
     * @return
     */
    List<CourseDetail> selectCourseDetailInfo(HashMap<String, Object> params);

    /**
     * 게시판 조회
     * @param params
     * @return
     */
    List<HashMap> selectBBSList(HashMap<String, Object> params);

    /**
     * 게시판상세 조회
     *
     * @param param
     * @return
     */
    HashMap selectBBSDetail(Map<String, Object> param);

    /**
     * 게시판등록
     *
     * @param param
     * @return
     */
    int insertBBS(Map<String, Object> param);

    /**
     * 게시판수정
     *
     * @param param
     * @return
     */
    int updateBBS(Map<String, Object> param);
}