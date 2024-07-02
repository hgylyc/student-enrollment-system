package com.kaifa.project.studentenrollmentsysytem.service;


import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Student_courseServiceTest {
    @Autowired
    public Student_courseService student_courseService;

    @Test
    public void getList(){
        List<Student_course> student_courses =student_courseService.list(null);
        student_courses.forEach(System.out::println);
    }

}
