package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 0/1

@RestController
@RequestMapping("/systemInform")
public class InformController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/InformContent/{stuId}")
    public List<Map<String,Object>> stuProcessState(@PathVariable String stuId) {
        return studentService.selectStateById(stuId);
    }
}
