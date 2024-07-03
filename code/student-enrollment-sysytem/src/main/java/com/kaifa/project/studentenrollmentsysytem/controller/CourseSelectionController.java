package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/CourseSel")
public class CourseSelectionController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private Student_courseService student_courseService;

    // 初始化课程列表
    @GetMapping("/course")
    public List<Course> getCourseForStu(HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        List<Course> courses = courseService.getCoursesByStudentAcademy(studentId);
        System.out.println("返回的课程数量: " + courses.size());
        return courses;
    }
    // 选课确认
    @PostMapping("/selectCourse")
    public String selectCourse(HttpSession session, @RequestParam("courseId") String courseId) {
        String s = (String) session.getAttribute("username");
        //return s;
        boolean isFull = courseService.isCourseFull(courseId);
        boolean isAlreadySelected = student_courseService.isCourseSelectByStu(s, courseId);
        if (isFull) {
            return "Course is already full";
        } else if (isAlreadySelected) {
            return "You have already selected this course";
        } else {
            Student_course sc = new Student_course();
            sc.setStudentId(s);
            sc.setCourseId(courseId);
            student_courseService.save(sc);
            courseService.updateNumOfStu(courseId);
            System.out.println("StudentID:"+s);
            return "Course selected successfully";
        }
    }

    // 通过课程名称和课程ID检索课程
    @GetMapping("/filter")
    public List<Course> filterCoursesByCourseNameAndId(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseId){
        return courseService.filterCoursesByCourseNameAndId(courseName, courseId);
    }

    // 课程类型过滤课程列表
    @GetMapping("/filterByType")
    public List<Course> filterCoursesByType(
            @RequestParam(required = false) String courseType){
        return courseService.filterCoursesByType(courseType);
    }
    @PostMapping("courseselect")   //查询
    public List<CourseDTO> courseSelect(@RequestBody CourseDTO courseDTO){
        List<Course> list =courseService.findCourses(courseDTO);
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }
}
