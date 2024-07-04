package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("information")
public class InformationController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;
    @PostMapping ("dormitories")
    public Result dormitoriesList (HttpSession session, Model model){
        String studentId = (String) session.getAttribute("username");
        // 如果studentId为空，返回空列表或者抛出异常
        if (studentId == null) {
            // 返回空列表或者抛出异常，根据需求选择
            return Result.error("用户id不存在，请重新登录",null);
        }
        Student student = studentService.getStudentById(studentId);
//        if ((student.getAreaNo()).equals("")) {
//            // 返回空列表或者抛出异常，根据需求选择
//            return Result.error("用户宿舍已存在",student.getAreaNo());
//        }
        // 获取该专业和性别对应的所有宿舍
            String academy = student.getAcademy();
            String gender = student.getGender();
            model.addAttribute("dormitoryList",studentService.getDormByAcGender(academy, gender));
            return Result.success("成功",studentService.getDormByAcGender(academy, gender));
    }
    @PostMapping("apply/{areano}/{dormno}/{roomno}")
    public Result applyForDormitory(@PathVariable String areano, @PathVariable String dormno, @PathVariable String roomno, HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        if (studentId == null) {
            return Result.error("用户未登录", null);
        }
        Student student = studentService.getStudentById(studentId);
        if(!(student.getDormNo().equals(""))){
            return Result.error("学生已有宿舍",studentId);
        }
        Dormitory dormitory = dormitoryService.applyForDormitory(studentId, areano, dormno, roomno);
        if (dormitory == null) {
            return Result.error("宿舍申请失败", null);
        }
        return Result.success("宿舍申请成功", dormitory);
    }
}
