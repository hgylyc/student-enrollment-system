package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("information")
public class InformationController {
    @Autowired
    private StudentService studentService;

//    @PostMapping("/information/dormitories")
//    public List<Dormitory> getDormitoriesByStudentId(HttpSession session){
//        String studentId = (String) session.getAttribute("studentId");
//        Student student = studentService.getStudentById(studentId);
//        // 获取该专业和性别对应的所有宿舍
//        if (studentId != null) {
//            String academy = student.getAcademy();
//            String gender = student.getGender();
//            return studentService.getDormitoriesByMajorAndGender(major, gender);
//        }
//
//        // 如果学生未找到，返回空列表或抛出异常
//        return List.of();
//    }
}
