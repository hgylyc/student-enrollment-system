package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // 获取所有教师
    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherService.list();
    }

    // 根据ID获取教师
    @GetMapping("/{id}")
    public Teacher getTeacherById(@PathVariable String id) {
        return teacherService.getById(id);
    }

    // 添加新教师
    @PostMapping("/add")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    // 更新教师信息
    @PutMapping("/update")
    public boolean updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateById(teacher);
    }

    // 删除教师
    @DeleteMapping("/delete/{id}")
    public boolean deleteTeacher(@PathVariable String id) {
        return teacherService.removeById(id);
    }
}
