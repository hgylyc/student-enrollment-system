package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;

import java.util.List;
import java.util.Map;

public interface InstituteService extends IService<Institute> {
    List<Map<String, Object>>  getTotalNumOfArrivedStu();
    List<Map<String, Object>> getStudentByInstitute();
    Institute getInstituteByName(String instituteName);
    boolean updateInstituteInfo(Institute institute);
    //返回学院报道率
    List<Map<String, Object>> getInstitutesWithLowestArrivalRate();
}