package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/Info")
public class InformationQueryController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/Steacher")
    public List<Teacher> getAllTeachersForStu(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/Ssearch")
    public Teacher getTeacherByNameForStu(@RequestParam String teacherName){
        return teacherService.getTeacherByName(teacherName);
    }

    @GetMapping("/Adm/Ateacher")
    public List<Teacher> getAllTeachersForAdm(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/Adm/Asearch")
    public Teacher getTeacherByNameForAdm(@RequestParam String teacherName){
        return teacherService.getTeacherByName(teacherName);
    }

    @GetMapping("/Adm/teacherDetails/{teacherId}")
    public Teacher getTeacherDetails(@PathVariable String teacherId) {
        return teacherService.getById(teacherId);
    }

    @PostMapping("/Adm/Addteacher")
    public ResponseEntity<String> addTeacher(@RequestParam("teacherId") String teacherId,
                                             @RequestParam("teacherName") String teacherName,
                                             @RequestParam("introduction") String introduction,
                                             @RequestParam("figureUrl") String figureUrl) {
        Teacher t = new Teacher();
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        t.setFigureUrl(figureUrl);
        boolean res = teacherService.addTeacher(t);
        if (res) {
            return ResponseEntity.ok("Teacher added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add teacher");
        }
    }

    @DeleteMapping("/Adm/Delteacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") String teacherId) {
        boolean res = teacherService.deleteTeacherById(teacherId);
        if (res) {
            return ResponseEntity.ok("Teacher deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete teacher");
        }
    }

    @PutMapping("/Adm/EditTeacher")
    public ResponseEntity<String> editTeacher(@RequestParam("teacherId") String teacherId,
                                              @RequestParam(value = "teacherName", required = false) String teacherName,
                                              @RequestParam(value = "tacademy", required = false) String tacademy,
                                              @RequestParam(value = "title", required = false) String title) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (teacherName != null) existingTeacher.setTeacherName(teacherName);
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);

            boolean res = teacherService.updateTeacher(existingTeacher);
            if (res) {
                return ResponseEntity.ok("Teacher updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update teacher");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        }
    }

    @PutMapping("/Adm/EditTeacherDetail")
    public ResponseEntity<String> editTeacherDetail(@RequestParam("teacherId") String teacherId,
                                                    @RequestParam(value = "teacherName", required = false) String teacherName,
                                                    @RequestParam(value = "tacademy", required = false) String tacademy,
                                                    @RequestParam(value = "title", required = false) String title,
                                                    @RequestParam(value = "temail", required = false) String temail,
                                                    @RequestParam(value = "introduction", required = false) String introduction,
                                                    @RequestParam(value = "figureUrl", required = false) String figureUrl) {
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
                return ResponseEntity.ok("Teacher updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update teacher");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Teacher not found");
        }
    }

    @PostMapping("/Adm/filterTeachers")
    public List<TeacherDTO> findTeachers(@RequestBody TeacherDTO teacherDTO){
        List<Teacher> list = teacherService.findTeachers(teacherDTO);
        return list.stream().map(TeacherDTO::new).collect(Collectors.toList());
    }
}