package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDetailsDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/AdmInfo")
public class TeacherInfoManagementController {
    @Autowired
    private TeacherService teacherService;
    //初始化管理端教师信息
    @GetMapping
    public List<Teacher> getAllTeachersForAdm() {
        return teacherService.getAllTeachers();
    }
    //管理端查询教师信息
    @GetMapping("/Asearch")
    public Teacher getTeacherByNameForAdm(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }
    // 详细信息
    @GetMapping("/teacherDetails/{teacherId}")
    public TeacherDetailsDTO getTeacherDetails(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        TeacherDetailsDTO teacherDetailsDTO = new TeacherDetailsDTO();
        teacherDetailsDTO.setTitle(teacher.getTitle());
        teacherDetailsDTO.setTeacherName(teacher.getTeacherName());
        teacherDetailsDTO.setTemail(teacher.getTemail());
        teacherDetailsDTO.setTacademy(teacher.getTacademy());
        teacherDetailsDTO.setIntroduction(teacher.getIntroduction());
        return teacherDetailsDTO;
    }

    //详细信息内编辑
    @PostMapping("/EditTeacherDetail")
    public String editTeacherDetail(@RequestParam("teacherId") String teacherId,
                                    @RequestParam(value = "tacademy", required = false) String tacademy,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "temail", required = false) String temail,
                                    @RequestParam(value = "introduction", required = false) String introduction,
                                    @RequestParam(value = "figureUrl", required = false) String figureUrl) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);
            if (temail != null) existingTeacher.setTemail(temail);
            if (introduction != null) existingTeacher.setIntroduction(introduction);
            if (figureUrl != null) existingTeacher.setFigureUrl(figureUrl);

            boolean res = teacherService.updateTeacher(existingTeacher);
            return res ? "Teacher updated successfully" : "Failed to update teacher";
        } else {
            return "Teacher not found";
        }
    }
    //删除教师信息
    @DeleteMapping("/Delteacher/{teacherId}")
    public String deleteTeacher(@PathVariable("teacherId") String teacherId) {
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
                             @RequestParam("introduction") String introduction,
                             @RequestParam("figureUrl") String figureUrl,
                             @RequestParam("title") String title,
                             @RequestParam("temail") String temail,
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
    public List<TeacherDTO> findTeachers(@RequestBody TeacherDTO teacherDTO) {
        List<Teacher> list = teacherService.findTeachers(teacherDTO);
        return list.stream().map(TeacherDTO::new).collect(Collectors.toList());
    }
}
