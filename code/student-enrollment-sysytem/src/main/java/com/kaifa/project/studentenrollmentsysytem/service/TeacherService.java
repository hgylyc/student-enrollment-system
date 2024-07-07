package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService extends IService<Teacher> {
    Teacher getTeacherByName(String teacherName);
    Teacher getTacherById(String teacherId);
    List<Teacher> getAllTeachers();
    boolean addTeacher(Teacher teacher);
    boolean updateTeacher(Teacher teacher);
    boolean deleteTeacherById(String teacherId);
    public List<Teacher> findTeachers(TeacherDTO filter);
}
