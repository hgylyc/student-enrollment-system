package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseTimeMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseTime;
import com.kaifa.project.studentenrollmentsysytem.service.CourseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTimeServiceImpl extends ServiceImpl<CourseTimeMapper, CourseTime> implements CourseTimeService {

    @Autowired
    private CourseTimeMapper courseTimeMapper;

    @Override
    public CourseTime getCourseTimeByCourseId(String courseId) {
        return courseTimeMapper.selectById(courseId);
    }


}