package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TeacherMapperTest {
    @Autowired
    public TeacherMapper teacherMapper;
    @Test
    public void getListTest(){
        List<Teacher> teacher = teacherMapper.selectList(null);
        teacher.forEach(System.out::println);
    }
}
