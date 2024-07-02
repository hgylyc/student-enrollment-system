package com.kaifa.project.studentenrollmentsysytem.service;

import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Test
    public void getList(){
        List<Course> list =courseService.list();
        list.forEach(System.out::println);
    }
}

