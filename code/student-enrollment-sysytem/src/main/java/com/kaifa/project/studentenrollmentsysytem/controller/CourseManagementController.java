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

    @GetMapping   //实现初始化
    public List<CourseDTO> initialClasses(){
        List<Course> list =courseService.list();
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    @PostMapping("coursecreate")    //创建课程
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

    @PostMapping("coursedelete")    //删除课程
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

    @PostMapping("courseselect")   //查询
    public List<CourseDTO> courseSelect(@RequestBody CourseDTO courseDTO){
        List<Course> list =courseService.findCourses(courseDTO);
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }
}
