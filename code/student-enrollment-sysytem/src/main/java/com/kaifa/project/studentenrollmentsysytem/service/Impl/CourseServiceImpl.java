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
        Course course = baseMapper.selectById(courseId);
        return course.getCeilingOfPersonnel() <= course.getCurrentNumOfStu();
    }

    @Override
    public void updateNumOfStu(String courseId) {
        Course course = baseMapper.selectById(courseId);
        course.setCurrentNumOfStu(course.getCurrentNumOfStu() + 1);
        baseMapper.updateById(course);
    }

    @Override
    public List<Course> filterCoursesByCourseNameAndId(String courseName, String courseId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        if (courseName != null && !courseName.isEmpty()) {
            queryWrapper.like("course_name", courseName);
        }

        if (courseId != null && !courseId.isEmpty()) {
            queryWrapper.eq("course_id", courseId);
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Course> filterCoursesByType(String courseType) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(courseType != null && !courseType.isEmpty()){
            queryWrapper.eq("course_type",courseType);
        }
        return baseMapper.selectList(queryWrapper);
    }


}

