package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.pojo.StudentDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.studentManageDTO;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Student getStudentById(String studentId) {
        return getById(studentId);
    }
    @Override
    public List<Dormitory> getDormByAcGender(String academy, String gender) {
        LambdaQueryWrapper<Dormitory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dormitory::getAcademy, academy)
                .eq(Dormitory::getGender, gender);
        return dormitoryMapper.selectList(queryWrapper);
    }
    @Override
    public void updateStudent(Student student) {
        studentMapper.updateById(student);
    }
    @Override
    public boolean updateStudentInfo(Student student) {
        // 更新学生信息到数据库
        return this.updateById(student);
    }

    public StudentDTO getStudentDTOById(String studentId) throws IOException {
        Student student = studentMapper.getStudentInfoById(studentId);

        if (student == null) {
            return null;
        }

        Path imagePath = Paths.get(student.getFigureUrl());
        byte[] imageBytes = Files.readAllBytes(imagePath);

        return new StudentDTO(
                student.getEmail(),
                student.getPhoneNumber(),
                student.getStudentId(),
                student.getAcademy(),
                imageBytes
        );
    }

    @Override
    public List<studentManageDTO> findStudents(String studentId, String studentName, String academy) {
        List<Student> students = studentMapper.selectStudents(studentId, studentName, academy);
        return students.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private studentManageDTO convertToDTO(Student student) {
        studentManageDTO dto = new studentManageDTO();
        dto.setStudentId(student.getStudentId());
        dto.setStudentName(student.getStudentName());
        dto.setAcademy(student.getAcademy());
        dto.setState1(student.isState1());
        dto.setState2(student.isState2());
        dto.setState3(student.isState3());
        return dto;
    }
    public List<Map<String, Object>> getNativeSpace(){
        return studentMapper.getNativeSpace();
    };
    public List<Map<String, Object>> getProcessState(){
        return studentMapper.getProcessState();
    };
    public List<Map<String, Object>> getTimeNode(){
        return studentMapper.getTimeNode();
    };
    public List<Map<String, Object>> selectStateById(String stuId){
        return studentMapper.selectStateById(stuId);
    };
}
