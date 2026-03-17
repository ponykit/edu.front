package com.edu.front.service;

import com.edu.front.dao.FrontDao;
import com.edu.front.model.course.*;
import com.edu.front.util.StringUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class FrontService {

    @Autowired
    FrontDao fontDao;

    /**
     * 메인리스트 조회
     *
     * @return
     */
    public  List<HashMap> selectMainList(HashMap<String, Object> params) {

        List<HashMap> rslt  = fontDao.selectMainList(params);

        return rslt;
    }

    /**
     * 강의리스트 조회
     *
     * @return
     */
    public  List<Course> selectCourseList(HashMap<String, Object> params) {
        List<Course> rslt  = fontDao.selectCourseList(params);

        return rslt;
    }

    /**
     * 강의리스트 상세
     *
     * @return
     */
    public  Course selectCourseDetail(HashMap<String, Object> params) {
        Course courseInfo = fontDao.selectCourseInfo(params);
        List<CourseDetail> courseDetail = fontDao.selectCourseDetailInfo(params);

        courseInfo.setCourseDetail(courseDetail);
        return courseInfo;
    }

    /**
     * 게시판 조회
     *
     * @return
     */
    public  List<HashMap> selectBBSList(HashMap<String, Object> params) {

        List<HashMap> rslt  = fontDao.selectBBSList(params);

        return rslt;
    }

    /**
     * 게시판 상세
     *
     * @return
     */
    public  HashMap selectBBSDetail(HashMap<String, Object> params) {
        return   fontDao.selectBBSDetail(params);
    }

    /**
     * 게시판등록
     *
     * @return
     */
    public  int insertBBS(HashMap<String, Object> params) {
        return  fontDao.insertBBS(params);
    }

    /**
     * 게시판수정
     *
     * @return
     */
    public  int updateBBS(HashMap<String, Object> params) {
        params.put("BoNum", Integer.parseInt(params.get("BoNum").toString()));
        return  fontDao.updateBBS(params);
    }
}
