package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDetailsDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/StuInfo")
public class TeacherInfoQueryController {

    @Autowired
    private TeacherService teacherService;
    //初始化学生教师信息面板
    @GetMapping
    public List<Teacher> getAllTeachersForStu() {
        return teacherService.getAllTeachers();

    }
    //查询教师信息
    @PostMapping("/Ssearch")
    public Teacher getTeacherByNameForStu(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }
    //筛选
    @PostMapping("/filterTeachers")
    public List<TeacherDTO> findTeachers(
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String tacademy,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String introduction,
            @RequestParam(required = false) String figureUrl,
            @RequestParam(required = false) String temail,
            @RequestParam(required = false) String institution) {
        // 如果 teacherName 不为空，则进行模糊查询处理
        if (teacherName != null && !teacherName.isEmpty()) {
            teacherName = "%" + teacherName + "%";
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherId(teacherId);
        teacherDTO.setTeacherName(teacherName);
        teacherDTO.setTacademy(tacademy);
        teacherDTO.setTitle(title);
        teacherDTO.setIntroduction(introduction);
        teacherDTO.setFigureUrl(figureUrl);
        teacherDTO.setTemail(temail);

        List<Teacher> list = teacherService.findTeachers(teacherDTO);
        return list.stream().map(TeacherDTO::new).collect(Collectors.toList());
    }




    // 获取教师详细信息，包括照片
    @PostMapping("/teacherDetails")
    public TeacherDetailsDTO getTeacherDetails(@RequestParam String teacherId) throws IOException {
        Teacher teacher = teacherService.getById(teacherId);
        TeacherDetailsDTO teacherDetailsDTO = new TeacherDetailsDTO();
        teacherDetailsDTO.setTitle(teacher.getTitle());
        teacherDetailsDTO.setTeacherName(teacher.getTeacherName());
        teacherDetailsDTO.setTemail(teacher.getTemail());
        teacherDetailsDTO.setTacademy(teacher.getTacademy());
        teacherDetailsDTO.setIntroduction(teacher.getIntroduction());
        teacherDetailsDTO.setFigureUrl(teacher.getFigureUrl()); // 设置教师照片URL
        teacherDetailsDTO.setTeacherId(teacher.getTeacherId());
        // 指定图片文件路径
        /*Path imagePath = Paths.get(url);
        // 读取图片文件为字节数组
        byte[] imageBytes = Files.readAllBytes(imagePath);*/
//        teacherDetailsDTO.setImageBytes(imageBytes);
        System.out.println("详细信息成功"+teacherId);
        return teacherDetailsDTO;
    }


}
