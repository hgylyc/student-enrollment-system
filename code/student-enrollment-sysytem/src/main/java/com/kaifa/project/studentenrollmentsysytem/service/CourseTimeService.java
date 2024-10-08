package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseTime;

import java.util.List;

public interface CourseTimeService extends IService<CourseTime> {
    CourseTime getCourseTimeByCourseId(String courseId);

}