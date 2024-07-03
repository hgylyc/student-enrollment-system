package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl <CourseMapper, Course> implements CourseService{
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Course> getCoursesByStudentAcademy(String studentId) {
        Student student = studentMapper.selectById(studentId);
        String academy = student.getAcademy();
        return baseMapper.selectList(new QueryWrapper<Course>().likeRight("course_id", academy));
    }

    @Override
    public boolean isCourseFull(String courseId) {
        return false;
    }

    @Override
    public void incrementCurrentNumOfStu(String courseId) {

    }

    @Override
    public List<Course> filterCourses(String studentId, String courseType, String courseName) {
        return null;
    }
}

