package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private StudentService studentService;
//    @PostMapping("cardinfo")
//    public Result cardinfo(HttpSession session){
//        String studentId = (String) session.getAttribute("username");
//        // 如果studentId为空，返回空列表或者抛出异常
//        if (studentId == null) {
//            // 返回空列表或者抛出异常，根据需求选择
//            return Result.error("用户id不存在，请重新登录",null);
//        }
//        Student student = studentService.getStudentById(studentId);
//        return Result.success("校园卡查询成功",student);
//    }
    @PostMapping("pre")
    public Result personIn(HttpSession session){
        String studentId = (String) session.getAttribute("username");
        // 如果studentId为空，返回空列表或者抛出异常
        if (studentId == null) {
            // 返回空列表或者抛出异常，根据需求选择
            return Result.error("用户id不存在，请重新登录",null);
        }
        Student student = studentService.getStudentById(studentId);
        return Result.success("信息查询成功",student);
    }
}
