package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Info")
public class InformationQueryController {

    @Autowired
    public TeacherService teacherService;
    private static final Logger logger = LoggerFactory.getLogger(InformationQueryController.class);

    @GetMapping("/Steacher")
    public String getAllTeachersForStu(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "Steacher"; // Ensure this matches your template name without .html extension
    }

    @GetMapping("/Ssearch")
    public String getTeacherByNameForStu(@RequestParam String teacherName, Model model) {
        Teacher teacher = teacherService.getTeacherByName(teacherName);
        model.addAttribute("teacher", teacher);
        return "Ssearch"; // Ensure this matches your template name without .html extension
    }
    @GetMapping("/Adm/Ateacher")
    public String getAllTeachersForAdm(Model model) {
        List<Teacher> teachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", teachers);
        return "Ateacher"; // Ensure this matches your template name without .html extension
    }
    @GetMapping("/Adm/Asearch")
    public String getTeacherByNameForAdm(@RequestParam String teacherName, Model model) {
        Teacher teacher = teacherService.getTeacherByName(teacherName);
        model.addAttribute("teacher", teacher);
        return "Asearch"; // Ensure this matches your template name without .html extension
    }
    @GetMapping("/Adm/teacherDetails/{teacherId}")
    public String getTeacherDetails(@PathVariable String teacherId, Model model) {
        Teacher teacher = teacherService.getById(teacherId);
        model.addAttribute("teacher", teacher);
        return "teacherDetails"; // Ensure this matches your template name without .html extension
    }
    @GetMapping("/Adm/EditTeacherForm/{teacherId}")
    public String showEditTeacherForm(@PathVariable String teacherId, Model model) {
        Teacher teacher = teacherService.getById(teacherId);
        model.addAttribute("teacher", teacher);
        return "EditTeacherForm"; // Ensure this matches your template name without .html extension
    }

    @PostMapping("/Adm/Addteacher")
    public String addTeacher(@RequestParam("teacherId") String teacherId,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam("introduction") String introduction,
                             @RequestParam("figureUrl") String figureUrl,
                             Model model) {
        Teacher t = new Teacher();
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        t.setFigureUrl(figureUrl);
        boolean res = teacherService.addTeacher(t);
        if (res) {
            model.addAttribute("message", "Teacher added successfully");
        } else {
            model.addAttribute("message", "Failed to add teacher");
        }
        return "result"; // Ensure this matches your template name without .html extension
    }

    @DeleteMapping("/Delteacher/{teacherId}")
    public String deleteTeacher(@PathVariable("teacherId") String teacherId, Model model) {
        boolean res = teacherService.deleteTeacherById(teacherId);
        if (res) {
            model.addAttribute("message", "Teacher deleted successfully");
            logger.info("Teacher with ID {} deleted successfully", teacherId);
        } else {
            model.addAttribute("message", "Failed to delete teacher");
            logger.error("Failed to delete teacher with ID {}", teacherId);
        }
        return "result"; // Ensure this matches your template name without .html extension
    }

    @PutMapping("/Adm/EditTeacher")
    public String editTeacher(@RequestParam("teacherId") String teacherId,
                              @RequestParam(value = "teacherName", required = false) String teacherName,
                              @RequestParam(value = "tacademy", required = false) String tacademy,
                              @RequestParam(value = "title", required = false) String title,
                              Model model) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (teacherName != null) existingTeacher.setTeacherName(teacherName);
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);

            boolean res = teacherService.updateTeacher(existingTeacher);
            if (res) {
                model.addAttribute("message", "Teacher updated successfully");
            } else {
                model.addAttribute("message", "Failed to update teacher");
            }
        } else {
            model.addAttribute("message", "Teacher not found");
        }
        return "result"; // Ensure this matches your template name without .html extension
    }

    @PostMapping("/Adm/EditTeacherDetail")
    public String editTeacherDetail(@RequestParam("teacherId") String teacherId,
                                    @RequestParam(value = "teacherName", required = false) String teacherName,
                                    @RequestParam(value = "tacademy", required = false) String tacademy,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "temail", required = false) String temail,
                                    @RequestParam(value = "introduction", required = false) String introduction,
                                    @RequestParam(value = "figureUrl", required = false) String figureUrl,
                                    Model model) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (teacherName != null) existingTeacher.setTeacherName(teacherName);
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);
            if (temail != null) existingTeacher.setTemail(temail);
            if (introduction != null) existingTeacher.setIntroduction(introduction);
            if (figureUrl != null) existingTeacher.setFigureUrl(figureUrl);

            boolean res = teacherService.updateTeacher(existingTeacher);
            if (res) {
                model.addAttribute("message", "Teacher updated successfully");
            } else {
                model.addAttribute("message", "Failed to update teacher");
            }
        } else {
            model.addAttribute("message", "Teacher not found");
        }
        return "result"; // Ensure this matches your template name without .html extension
    }

    @PostMapping("/Adm/filterTeachers")
    public String findTeachers(@RequestBody TeacherDTO teacherDTO, Model model) {
        List<Teacher> list = teacherService.findTeachers(teacherDTO);
        List<TeacherDTO> teacherDTOList = list.stream().map(TeacherDTO::new).collect(Collectors.toList());
        model.addAttribute("teachers", teacherDTOList);
        return "filterTeachers"; // Ensure this matches your template name without .html extension
    }
}