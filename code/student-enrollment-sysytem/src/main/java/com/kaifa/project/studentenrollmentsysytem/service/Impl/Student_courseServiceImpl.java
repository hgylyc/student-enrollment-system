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

    @Autowired
    public Student_courseMapper student_courseMapper;
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
        System.out.println("Student courses (raw): " + studentCourses); // 调试日志，输出学生课程的原始数据

        List<CourseDTO> selectedCourses = studentCourses.stream()
                .map(sc -> {
                    Course course = courseMapper.selectById(sc.getCourseId());
                    // 检查 course 是否为空
                    if (course != null) {
                        System.out.println("Course found for courseId: " + sc.getCourseId()); // 调试日志
                        return new CourseDTO(course);
                    } else {
                        System.out.println("Course not found for courseId: " + sc.getCourseId()); // 调试日志
                        return null;
                    }
                })
                .filter(courseDTO -> courseDTO != null) // 过滤掉空的 courseDTO
                .collect(Collectors.toList());

        System.out.println("Selected courses (filtered): " + selectedCourses); // 调试日志，输出过滤后的课程数据
        return selectedCourses;
    }

    @Override
    public void dropCourse(String studentId, String courseId) {
        baseMapper.delete(new QueryWrapper<Student_course>()
                .eq("student_id", studentId)
                .eq("course_id", courseId));
    }

    @Override
    public List<String> getSelectedCourseIdsByStudentId(String studentId) {
        return student_courseMapper.getSelectedCourseIdsByStudentId(studentId);
    }
}