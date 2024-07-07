package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDetailsDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/AdmInfo")
public class TeacherInfoManagementController {
    @Autowired
    private HttpSession session;
    @GetMapping
    public Object getAllTeachersForAdm() {
        // 判断角色是否为老师
        /*Object role = session.getAttribute("role");*/
        Object role = "teacher";
        if (role == null || !role.equals("teacher")) {
            return "Unauthorized"; // 或者根据需求返回其他信息或处理方式
        }
        System.out.println("管理端初始化成功");
        return teacherService.getAllTeachers();
    }
    @Autowired
    private TeacherService teacherService;
    //初始化管理端教师信息
    /*@GetMapping
    public List<Teacher> getAllTeachersForAdm() {
        return teacherService.getAllTeachers();
    }*/
    //管理端查询教师信息
/*    @GetMapping("/Asearch")
    public Teacher getTeacherByNameForAdm(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }*/
    // 获取教师详细信息，包括照片
    @PostMapping("/teacherDetails")
    public TeacherDetailsDTO getTeacherDetails(@RequestParam String teacherId/*, HttpSession session*/) {
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

        System.out.println("管理端详细信息成功"+teacherId);
        return teacherDetailsDTO;
    }

    //详细信息内编辑
    @PostMapping("/EditTeacherDetail")
    public String editTeacherDetail(@RequestParam("teacherId") String teacherId,
                                    @RequestParam(value = "tacademy", required = false) String tacademy,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "temail", required = false) String temail,
                                    @RequestParam(value = "introduction", required = false) String introduction,
                                    @RequestParam(value = "figureUrl", required = false) String figureUrl,
                                    @RequestParam(value = "teacherName", required = false) String teacherName) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);
            if (temail != null) existingTeacher.setTemail(temail);
            if (introduction != null) existingTeacher.setIntroduction(introduction);
            if (figureUrl != null) existingTeacher.setFigureUrl(figureUrl);
            if(teacherName!=null) existingTeacher.setTeacherName(teacherName);

            boolean res = teacherService.updateTeacher(existingTeacher);
            return res ? "Teacher updated successfully" : "Failed to update teacher";
        } else {
            return "Teacher not found";
        }
    }
    @PostMapping("/Delteacher")
    public String deleteTeacher(@RequestParam String teacherId) {
        // 校验 teacherId 是否为空
        if (teacherId == null || teacherId.isEmpty()) {
            return "Teacher ID is required";
        }

        // 删除教师信息
        boolean res = teacherService.deleteTeacherById(teacherId);
        return res ? "Teacher deleted successfully" : "Failed to delete teacher";
    }
    //编辑
    @PutMapping("/EditTeacher")
    public String editTeacher(@RequestParam("teacherId") String teacherId,
                              @RequestParam(value = "teacherName", required = false) String teacherName,
                              @RequestParam(value = "tacademy", required = false) String tacademy,
                              @RequestParam(value = "title", required = false) String title) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (teacherName != null) existingTeacher.setTeacherName(teacherName);
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);

            boolean res = teacherService.updateTeacher(existingTeacher);
            return res ? "Teacher updated successfully" : "Failed to update teacher";
        } else {
            return "Teacher not found";
        }
    }
    //创建教师
    @PostMapping("/Addteacher")
    public String addTeacher(@RequestParam("teacherId") String teacherId,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam(value ="introduction", required = false) String introduction,
                             @RequestParam(value ="figureUrl",required = false) String figureUrl,
                             @RequestParam("title") String title,
                             @RequestParam(value = "temail",required = false) String temail,
                             @RequestParam("tacademy") String tacademy
                             ) {
        Teacher t = new Teacher();
        t.setTacademy(tacademy);
        t.setTitle(title);
        t.setTemail(temail);
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        t.setFigureUrl(figureUrl);
        boolean res = teacherService.addTeacher(t);
        return res ? "Teacher added successfully" : "Failed to add teacher";
    }
    //检索
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

}
