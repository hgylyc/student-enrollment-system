package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.StudentDTO;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/person")
public class PerController {
    @Autowired
    StudentService studentService;
    @GetMapping("details")
    public ResponseEntity<StudentDTO> getStudentDetails(HttpSession session) throws IOException {
        String id = (String)session.getAttribute("username");
        StudentDTO studentDetails = studentService.getStudentDTOById(id);

        if (studentDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(studentDetails);
    }

}
