package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/StuInfo")
public class TeacherInfoQueryController {

    @Autowired
    private TeacherService teacherService;
    //初始化学生教师信息面板
    @GetMapping
    public List<Teacher> getAllTeachersForStu() {
        return teacherService.getAllTeachers();
    }
    //查询教师信息
    @GetMapping("/Ssearch")
    public Teacher getTeacherByNameForStu(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }
}
