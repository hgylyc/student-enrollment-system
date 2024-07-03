package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;

import java.util.List;

public interface CourseService extends IService <Course>{
    List<Course> getCoursesByStudentAcademy(String studentId);
    boolean isCourseFull(String courseId);
    void updateNumOfStu(String courseId);
    List<Course> filterCoursesByCourseNameAndId(String courseName, String courseId);
    List<Course> filterCoursesByType(String courseType);
    List<Course> findCourses(CourseDTO filter);

}
