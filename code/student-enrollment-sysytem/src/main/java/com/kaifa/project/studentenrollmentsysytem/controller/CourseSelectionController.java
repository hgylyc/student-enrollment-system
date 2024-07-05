package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
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
    @GetMapping
    public List<CourseDTO> getCourseForStu(HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        List<Course> courses = courseService.getCoursesByStudentAcademy(studentId);
        return courses.stream().map(course -> {
            CourseDTO courseDTO = new CourseDTO(course);
            boolean isFull = courseService.isCourseFull(course.getCourseId());
            boolean isAlreadySelected = student_courseService.isCourseSelectByStu(studentId, course.getCourseId());
            if (isFull) {
                courseDTO.setStatus("已满");
            } else if (isAlreadySelected) {
                courseDTO.setStatus("已选");
            } else {
                courseDTO.setStatus("");
            }
            return courseDTO;
        }).collect(Collectors.toList());
    }

    // 选课确认
    @PostMapping("/selectCourse")
    public String selectCourse(HttpSession session, @RequestBody CourseDTO courseDTO) {
        String studentId = (String) session.getAttribute("username");
        String courseId = courseDTO.getCourseId(); // 获取 courseId
        boolean isFull = courseService.isCourseFull(courseId);
        boolean isAlreadySelected = student_courseService.isCourseSelectByStu(studentId, courseId);

        if (isFull) {
            return "Course is already full";
        } else if (isAlreadySelected) {
            return "You have already selected this course";
        } else {
            Student_course sc = new Student_course();
            sc.setStudentId(studentId);
            sc.setCourseId(courseId);
            student_courseService.save(sc);
            courseService.updateNumOfStu(courseId);
            return "Course selected successfully";
        }
    }

    // 退课操作
    @PostMapping("/dropCourse")
    public String dropCourse(HttpSession session, @RequestBody CourseDTO courseDTO) {
        String studentId = (String) session.getAttribute("username");
        String courseId = courseDTO.getCourseId();
        student_courseService.dropCourse(studentId, courseId);
        courseService.decreaseNumOfStu(courseId);
        return "Course dropped successfully";
    }

    // 通过课程名称和课程ID检索课程
    @GetMapping("/filter")
    public List<CourseDTO> filterCoursesByCourseNameAndId(
            @RequestParam(required = false) String courseName,
            @RequestParam(required = false) String courseId) {
        List<Course> courses = courseService.filterCoursesByCourseNameAndId(courseName, courseId);
        return courses.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    // 课程类型过滤课程列表
    @GetMapping("/filterByType")
    public List<CourseDTO> filterCoursesByType(
            @RequestParam(required = false) String courseType) {
        List<Course> courses = courseService.filterCoursesByType(courseType);
        return courses.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    // 查询课程
    @PostMapping("/searchCourse")
    public List<CourseDTO> searchCourse(@RequestBody CourseDTO courseDTO) {
        List<Course> list = courseService.findCourses(courseDTO);
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    // 获取课程详细信息及教师列表
    @GetMapping("/courseDetails")
    public CourseDTO getCourseDetails(@RequestParam("courseId") String courseId) {
        return courseService.getCourseDetails(courseId);
    }

    // 获取已选课程列表
    @GetMapping("/selectedCourses")
    public List<CourseDTO> getSelectedCourses(HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        return student_courseService.getSelectedCourses(studentId);
    }


}
