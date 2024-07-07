package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.pojo.studentManageDTO;
import com.kaifa.project.studentenrollmentsysytem.service.InstituteService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@RestController
@RequestMapping("manage")
public class StudentManageController {
    @Autowired
    StudentService studentService;
    @Autowired
    InstituteService instituteService;
    //管理端查询学生信息
    @PostMapping("/findStudents")
    public Result findStudents(@RequestParam(value = "studentId", required = false) String studentId,
                               @RequestParam(value = "studentName", required = false) String studentName,
                               @RequestParam(value = "academy", required = false) String Academy) {
        List<studentManageDTO>students=new ArrayList<>();
        if(!(Academy==null)&&!(Academy.isEmpty())){
            String academy=Character.toString(Mapping.mapCollege(Academy));
            System.out.println(academy);
            students = studentService.findStudents(studentId, studentName, academy);

        }
        else{
            students = studentService.findStudents(studentId, studentName, Academy);
        }
        if (students.isEmpty()) {
            return Result.error("未找到匹配的学生信息", null);
        }
        return Result.success("查询成功", students);
    }
    //查询学生详细信息
    @PostMapping("/findStudents/details")
    public Result StudentDetail(@RequestParam("id") String id, HttpSession session) {
        Student student = studentService.getStudentById(id);
        return Result.success("详细信息", student);
    }
    @PostMapping("/findStudents/state")
    public Result UpdateState(@RequestParam("id") String id) {
        Student student = studentService.getStudentById(id);
        // 查找学生的学院
        Institute institute = instituteService.getInstituteByName(student.getAcademy());
        if (!student.isState3()) {
            student.setState3(true);
            student.setTimeNode(LocalDateTime.now());
            institute.setNumofarrivedstu(institute.getNumofarrivedstu() + 1);
        }
        else {
            student.setState3(false);
            student.setTimeNode(null);
            institute.setNumofarrivedstu(institute.getNumofarrivedstu() - 1);
        }
        boolean updateResult = studentService.updateStudentInfo(student);
        instituteService.updateInstituteInfo(institute);
        if (updateResult) {
                return Result.success("状态修改成功", student);
            } else {
                return Result.error("状态修改失败", null);
            }
        }
    }

