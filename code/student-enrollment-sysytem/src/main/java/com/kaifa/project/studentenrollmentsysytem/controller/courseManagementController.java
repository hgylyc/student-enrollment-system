package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseCreate;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/coursemanage")
public class courseManagementController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Course> initialClasses(){
        List<Course> list =courseService.list();
        return list;
    }

    @PostMapping("courseCreate")
    public String courseCreate(HttpSession session,@RequestBody CourseCreate course) {
        if (session.getAttribute("role") != "teacher"){
            //return "role wrong";
        }
        //检验teacher字段合法性
        Teacher teacher = teacherService.getById(course.teacherId);
        if(teacher==null)
                return "teacher not exist";
        else if(!teacher.getTeacherName().equals(course.getTeacherName()))
        {
            return "teacher name wrong";
        }
        //
        return "asdasd";
    }
}
