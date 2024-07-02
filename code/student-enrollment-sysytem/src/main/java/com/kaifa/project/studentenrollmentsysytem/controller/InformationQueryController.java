package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Info")
public class InformationQueryController {

    @Autowired
    private TeacherService teacherService;

    //学生端初始化页面，获取所有教师信息
    @GetMapping("/Steacher")
    public List<Teacher> getAllTeachersForStu(){
        return teacherService.getAllTeachers();
    }
    //学生端通过教师姓名查询教师信息
    @GetMapping("/Sserach")
    public Teacher getTeacherByNameForStu(@RequestParam String teacherName){
        return teacherService.getTeacherByName(teacherName);
    }
    //管理端初始化页面
    @GetMapping("/Ateacher")
    public List<Teacher> getAllTeachersForAdm(){
        return teacherService.getAllTeachers();
    }
    //管理端通过教师姓名查询教师信息
    @GetMapping("/Asearch")
    public Teacher getTeacherByNameForAdm(@RequestParam String teacherName){
        return teacherService.getTeacherByName(teacherName);
    }
    //管理端添加教师信息
    @PostMapping("/Ateacher")
    public String addTeacher(@RequestBody Teacher teacher) {
        boolean res = teacherService.addTeacher(teacher);
        if (res) {

            return "redirect:/info/Ateacher";
        } else {
            return "Failed to add teacher";
        }
    }
    // 管理端删除教师信息
    @DeleteMapping("/Ateacher/{teacherId}")
    public String deleteTeacher(@PathVariable("teacherId") String teacherId) {
        boolean res = teacherService.deleteTeacherById(teacherId);
        if (res) {
            return "redirect:/info/Ateacher";
        } else {
            return "Failed to delete teacher";
        }
    }
    // 管理端更新教师信息
    @PutMapping("/admin/teacher")
    public String updateTeacher(@RequestBody Teacher teacher) {
        boolean result = teacherService.updateTeacher(teacher);
        if (result) {
            return "redirect:/info/teachers";
        } else {
            return "Failed to update teacher";
        }
    }

}
