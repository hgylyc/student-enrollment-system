package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;

import java.util.List;
import java.util.Map;

public interface CourseService extends IService <Course>{
    List<Course> getCoursesByStudentAcademy(String studentId);
    boolean isCourseFull(String courseId);
    void updateNumOfStu(String courseId);
    void decreaseNumOfStu(String coureseId);
    List<Course> filterCoursesByCourseNameAndId(String courseName, String courseId);
    List<Course> filterCoursesByType(String courseType);

    public boolean updateCourse(Course course);

    List<Course> findCourses(CourseDTO filter);

    CourseDTO getCourseDetails(String courseId);

    Course getCourseById(String courseId);
    //返回10个选课率最低的课程信息
    List<Map<String, Object>> getLowestEnrollmentRateCourses();
}
