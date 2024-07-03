package com.kaifa.project.studentenrollmentsysytem.controller;


import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("coursequery")
public class CourseQueryController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> initialClasses(){
        List<Course> list =courseService.list();
        return list;
    }

    @PostMapping("search")
    public void courseSearch(@RequestParam("") String username,
                             @RequestParam("password") String password){

    }


}
