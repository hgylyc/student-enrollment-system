package com.kaifa.project.studentenrollmentsysytem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDetailsDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/StuInfo")
public class TeacherInfoQueryController {
    @Autowired
    private TeacherService teacherService;
    // 初始化学生教师信息面板
    @PostMapping
    public ResponseEntity<?> getAllTeachersForStu(@RequestParam("currentPage") int currentPage,
                                                  @RequestParam("pageSize") int pageSize) {
        PageHelper.startPage(currentPage, pageSize); // 启动分页
        List<Teacher> list = teacherService.getAllTeachers();
        PageInfo<Teacher> pageInfo = new PageInfo<>(list); // 获取分页信息
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("访问出错或无数据");
        } else {
            System.out.println("分页成功");
            Map<String, Object> response = new HashMap<>();
            response.put("teachers", pageInfo.getList());
            response.put("total", pageInfo.getTotal());
            return ResponseEntity.ok(response);
        }
    }
    //查询教师信息
    @PostMapping("/Ssearch")
    public Teacher getTeacherByNameForStu(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }
    //筛选
    @PostMapping("/filterTeachers")
    public ResponseEntity<?> findTeachers(
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String tacademy,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize) {

        // 如果 teacherName 不为空，则进行模糊查询处理
        if (teacherName != null && !teacherName.isEmpty()) {
            teacherName = "%" + teacherName + "%";
        }
        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherId(teacherId);
        teacherDTO.setTeacherName(teacherName);
        teacherDTO.setTacademy(tacademy);
        // 启动分页
        PageHelper.startPage(currentPage, pageSize);
        List<Teacher> list = teacherService.findTeachers(teacherDTO, currentPage, pageSize);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);
        int total = teacherService.countFilteredTeachers(teacherDTO);
        System.out.println("total: " + total);
        Map<String, Object> response = new HashMap<>();
        response.put("teachers", pageInfo.getList().stream().map(TeacherDTO::new).collect(Collectors.toList()));
        response.put("total", String.valueOf(total)); // 将 total 转换为字符串
        return ResponseEntity.ok(response);
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
