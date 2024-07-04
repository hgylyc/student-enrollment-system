package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.mapper.TeacherMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    public TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacherByName(String teacherName) {
        return baseMapper.selectOne(new QueryWrapper<Teacher>().eq("teacher_name", teacherName));
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return baseMapper.insert(teacher) > 0;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return baseMapper.updateById(teacher) > 0;
    }
    @Override
    public boolean deleteTeacherById(String teacherId) {
        try {
            int result = teacherMapper.deleteById(teacherId);
            if (result > 0) {
                System.out.println("Teacher with ID " + teacherId + " deleted successfully.");
            } else {
                System.out.println("Failed to delete teacher with ID " + teacherId);
            }
            return result > 0;
        } catch (Exception e) {
            // Log the exception
            System.err.println("Exception occurred while deleting teacher: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Teacher> findTeachers(TeacherDTO filter) {
        return baseMapper.selectTeachers(filter);
    }
}
