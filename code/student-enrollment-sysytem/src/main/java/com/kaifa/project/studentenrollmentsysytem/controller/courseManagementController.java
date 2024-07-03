package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseCreate;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coursemanage")
public class courseManagementController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<CourseDTO> initialClasses(){
        List<Course> list =courseService.list();
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    @PostMapping("coursecreate")
    public String courseCreate(HttpSession session,@RequestBody CourseCreate createCourse) {
        if (!session.getAttribute("role") .equals("teacher") ){
            return "role wrong";
        }
        //检验teacher字段合法性
        Teacher teacher = teacherService.getById(createCourse.teacherId);
        if(teacher==null)
                return "teacher not exist";
        else if(!teacher.getTeacherName().equals(createCourse.getTeacherName()))
        {
            return "teacher name wrong";
        }
        Course course=new Course(createCourse);
        courseService.save(course);
        return course.getCourseId();
    }

    @PostMapping("coursedelete")
    public String courseDelete(HttpSession session,@RequestParam("courseId") String courseId){
        System.out.println(session.getAttribute("role"));
        if (!session.getAttribute("role") .equals( "teacher")){
            return "role wrong";
        }
        boolean isRemoved=courseService.removeById(courseId);
        if(isRemoved)
            return "success";
        else
            return "fail";
    }

    @PostMapping("courseselect")
    public String courseSelect(@RequestParam("institution") String institution,
                               @RequestParam("status") String status,
                               @RequestParam("teacherName") String teacherName,
                               @RequestParam("Filled") String Filled,
                               @RequestParam("score") String score){
        return "";
    }
}
