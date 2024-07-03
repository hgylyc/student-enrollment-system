package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;

import java.util.List;
import java.util.Map;

public interface InstituteService extends IService<Institute> {
    Integer getTotalNumOfArrivedStu();
    List<Map<String, Object>> getStudentByInstitute();
}