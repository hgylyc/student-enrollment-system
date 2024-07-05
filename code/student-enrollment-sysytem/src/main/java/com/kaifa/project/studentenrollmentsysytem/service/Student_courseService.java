package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;

import java.util.List;

public interface Student_courseService extends IService<Student_course> {
    boolean isCourseSelectByStu(String studentId, String courseId);

    List<Student_course> getStudentSchedule(String studentId);

    List<CourseDTO> getSelectedCourses(String studentId);

    void dropCourse(String studentId, String courseId);
}