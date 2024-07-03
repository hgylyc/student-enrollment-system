package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
