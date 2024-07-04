package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class StudentMapperTest {
    @Autowired
    public StudentMapper studentMapper;
    @Test
    public void getlistTest(){
        List<Student> student=studentMapper.selectList(null);
        student.forEach(System.out::println);
    }
    @Test
    public void testInsertStudent() {
        Student student = new Student();
        student.setStudentName("xjh");
        student.setGender("男");
        student.setNativeSpace("安徽合肥");
        student.setStudentId("20221410");
        student.setClassNo("CS101");
        student.setAcademy("C");
        student.setMajor("Computer Science");
        student.setAreaNo("");
        student.setDormNo("");
        student.setRoomNo("");
        student.setBedNo("1");
        student.setState1(true);
        student.setState2(false);
        student.setState3(true);
        student.setTimeNode(LocalDateTime.now());
        student.setFigureUrl("http://example.com/figure.jpg");
        student.setIdNumber("987654321");
        student.setFatherName("John Doe Sr.");
        student.setMotherName("Jane Doe");
        student.setEmergencyContactName("Emergency Contact");
        student.setEmergencyContactTel("1234567890");
        student.setHomeAddress("123 Main St, Anytown, USA");
        student.setEmail("johndoe@example.com");
        student.setSchoolCardPassword("password123");
        student.setSchoolCardBalance(100);

        int result = studentMapper.insert(student);
        System.out.println("插入用户"+result);
    }
}
