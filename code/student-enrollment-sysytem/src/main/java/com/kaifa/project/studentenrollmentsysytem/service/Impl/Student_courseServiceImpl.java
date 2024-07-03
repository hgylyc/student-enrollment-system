package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.Student_courseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.stereotype.Service;

@Service
public class Student_courseServiceImpl extends ServiceImpl<Student_courseMapper, Student_course>implements Student_courseService {
    @Override
    public boolean isCourseSelectByStu(String studentId, String courseId) {
        return baseMapper.selectOne(new QueryWrapper<Student_course>()
                .eq("student_id",studentId)
                .eq("course_id",courseId))!=null;
    }
}
