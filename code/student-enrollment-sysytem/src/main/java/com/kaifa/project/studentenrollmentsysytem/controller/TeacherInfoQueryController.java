package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/filterTeachers")
    public List<TeacherDTO> findTeachers(@RequestBody TeacherDTO teacherDTO) {
        List<Teacher> list = teacherService.findTeachers(teacherDTO);
        return list.stream().map(TeacherDTO::new).collect(Collectors.toList());
    }
}
