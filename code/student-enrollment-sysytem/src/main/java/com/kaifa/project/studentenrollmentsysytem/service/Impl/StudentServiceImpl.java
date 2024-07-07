package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Override
    public Student getStudentById(String studentId) {

        Student student= getById(studentId);
        String a=student.getAcademy();
        char b=a.charAt(0);
        student.setAcademy(Mapping.reverseMapCollege(b));
        return student;
    }
    public DormitoryDTO convertToDTO(Dormitory dormitory) {
        DormitoryDTO dto = new DormitoryDTO();
        dto.setAreano(dormitory.getAreano());
        dto.setDormno(dormitory.getDormno());
        dto.setRoomno(dormitory.getRoomno());
        dto.setMaxnumofstu(dormitory.getMaxnumofstu());
        dto.setCurrentnumofstu(dormitory.getCurrentnumofstu());
        dto.setGender(dormitory.getGender());
        dto.setAcademy(Mapping.reverseMapCollege(dormitory.getAcademy().charAt(0)));
        dto.setIsFull(dormitory.getIsFull());
        return dto;
    }
    @Override
    public List<DormitoryDTO> getDormByAcGender(String academy, String gender) {
        LambdaQueryWrapper<Dormitory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dormitory::getAcademy, academy)
                .eq(Dormitory::getGender, gender);
        List<Dormitory> dormitories = dormitoryMapper.selectList(queryWrapper);

        // 将 Dormitory 列表转换为 DormitoryDTO 列表
        List<DormitoryDTO> dormitoryDTOs = dormitories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return dormitoryDTOs;
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
        if (student.getAcademy() == null) {
            dto.setAcademy(student.getAcademy());}
        else{
            dto.setAcademy(Mapping.reverseMapCollege((student.getAcademy()).charAt(0)));
        }
        dto.setState1(student.isState1());
        dto.setState2(student.isState2());
        dto.setState3(student.isState3());
        return dto;
    }
    //查看舍友
    public List<Map<String, Object>> findStudentsByDormitory(String areaNo, String dormNo, String roomNo) {
        List<Map<String, Object>> students =studentMapper.findStudentsByDormitory(areaNo, dormNo, roomNo);
        students.forEach(student ->{
            String NameStr = (String)student.get("academy");
            char a = NameStr.charAt(0);
            student.put("Academy", Mapping.reverseMapCollege(a));
        });
        return students;
    }
    //
    public List<Map<String, Integer>> getDailyReportCount() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Map<String, Integer>> dailyCountList = new ArrayList<>();

        for (int i = 5; i <= 9; i++) {
            String date = LocalDate.of(2024, 7, i).format(formatter);
            int count = studentMapper.countByDate(date);

            Map<String, Integer> dailyCount = new HashMap<>();
            dailyCount.put(date, count);
            dailyCountList.add(dailyCount);
        }

        return dailyCountList;
    }

    public Map<String, Integer> getTodayReportCount() {
        LocalDate today = LocalDate.now();
        String todayDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int count = studentMapper.countByDate(todayDate);

        Map<String, Integer> response = new HashMap<>();
        response.put(todayDate, count);
        return response;
    }
    @Override
    public List<Map<String, Object>> findStusByDormitory(String areano, String dormno, String roomno) {
        List<Map<String, Object>> students=studentMapper.findStusByDormitory(areano, dormno, roomno);
        students.forEach(student ->{
            String NameStr = (String)student.get("academy");
            char a = NameStr.charAt(0);
            student.put("Academy", Mapping.reverseMapCollege(a));
        });
        return students;
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
