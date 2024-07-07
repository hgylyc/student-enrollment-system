package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;
    @GetMapping("/all")
    public List<Dormitory> index(Model model){
        List<Dormitory> list = dormitoryService.list();
        return list;
    }
    @PostMapping("/myDormitory")   //查询
    public List<Map<String, Object>> myDor(HttpSession session){
        if (!session.getAttribute("role") .equals("student") ){
            System.out.println("cnm");
            return null;
        }
        System.out.println(session.getAttribute("username"));
        String username = (String) session.getAttribute("username");
        List<Map<String, Object>> list =dormitoryService.myDormitory(username);
        return list;
    }
}
