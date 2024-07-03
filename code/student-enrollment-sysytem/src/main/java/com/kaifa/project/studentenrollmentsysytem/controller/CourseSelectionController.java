package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CourseSel")
public class CourseSelectionController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private Student_courseService student_courseService;

    //初始化课程列表
    @GetMapping("/course/{studentId}")
    public List<Course> getCourseForStu(@PathVariable("studentId") String studentID){
        return courseService.getCoursesByStudentAcademy(studentID);
    }
    //选课确认
    @PostMapping("/selectCourse")
    public String selectCourse(
            @RequestParam("studentId") String studentId,
            @RequestParam("courseId") String courseId,
            @RequestParam("courseName") String courseName){
        boolean isFull = courseService.isCourseFull(courseId);
        boolean isAlreadySelected = student_courseService.isCourseSelectByStu(studentId,courseId);
        if(isFull){
            return "Course is already full";
        } else if (isAlreadySelected) {
            return "You have already selected this course";
        } else {
            Student_course sc =new Student_course();
            sc.setStudentId(studentId);
            sc.setCourseId(courseId);
            student_courseService.save(sc);
            courseService.updateNumOfStu(courseId);
            return "Course selected successfully";
        }
    }
    //通过课程名称和课程ID检索课程
    @GetMapping("/filter")
    public List<Course> filterCoursesByCourseNameAndId(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseId){
        return courseService.filterCoursesByCourseNameAndId(courseName,courseId);
    }
    //课程类型过滤课程列表
    @GetMapping("/filterByType")
    public List<Course> filterCoursesByType(
            @RequestParam(required = false) String courseType
    ){
        return courseService.filterCoursesByType(courseType);
    }
}
