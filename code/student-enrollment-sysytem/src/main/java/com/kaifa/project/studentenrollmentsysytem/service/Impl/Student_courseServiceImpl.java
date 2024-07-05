package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.Student_courseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Student_courseServiceImpl extends ServiceImpl<Student_courseMapper, Student_course> implements Student_courseService {
    @Autowired
    public CourseMapper courseMapper;
    @Override
    public boolean isCourseSelectByStu(String studentId, String courseId) {
        return baseMapper.selectOne(new QueryWrapper<Student_course>()
                .eq("student_id", studentId)
                .eq("course_id", courseId)) != null;
    }

    @Override
    public List<Student_course> getStudentSchedule(String studentId) {
        return baseMapper.selectList(new QueryWrapper<Student_course>().eq("student_id", studentId));
    }

    @Override
    public List<CourseDTO> getSelectedCourses(String studentId) {
        List<Student_course> studentCourses = getStudentSchedule(studentId);
        return studentCourses.stream()
                .map(sc -> {
                    Course course = courseMapper.selectById(sc.getCourseId());
                    // 检查 course 是否为空
                    if (course != null) {
                        return new CourseDTO(course);
                    } else {
                        // 根据需求处理 course 为空的情况
                        return null;
                    }
                })
                .filter(courseDTO -> courseDTO != null) // 过滤掉空的 courseDTO
                .collect(Collectors.toList());
    }

    @Override
    public void dropCourse(String studentId, String courseId) {
        baseMapper.delete(new QueryWrapper<Student_course>()
                .eq("student_id", studentId)
                .eq("course_id", courseId));
    }
}