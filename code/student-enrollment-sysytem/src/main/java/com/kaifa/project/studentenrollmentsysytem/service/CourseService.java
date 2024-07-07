package com.kaifa.project.studentenrollmentsysytem.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;



import java.util.Map;

@Service
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

    Map<String, Object> getCourseStatistics();

    Course getCourseById(String courseId);


    CourseDTO getCourseDetailsByCourseName(String courseName);

    List<Course> getCoursesExcludingStudentAcademy(String studentId);

    List<Course> getAllCourses();

    //返回10个选课率最低的课程信息
    List<Map<String, Object>> getLowestEnrollmentRateCourses();

    void updateTeacherNameInCourses(String teacherId, String teacherName);
}
