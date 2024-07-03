package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import java.util.List;

public interface CourseService extends IService <Course>{
    List<Course> getCoursesByStudentAcademy(String studentId);
    boolean isCourseFull(String courseId);
    void incrementCurrentNumOfStu(String courseId);
    List<Course> filterCourses(String studentId, String courseType, String courseName);
}
