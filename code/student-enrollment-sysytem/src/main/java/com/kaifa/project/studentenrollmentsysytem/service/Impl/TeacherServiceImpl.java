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
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("teacher_name", teacherName));
        System.out.println("查询教师成功");
        return teacher;
    }

    @Override
    public Teacher getTacherById(String teacherId) {
        Teacher teacher = baseMapper.selectOne(new QueryWrapper<Teacher>().eq("teacher_id", teacherId));
        System.out.println("查询教师成功");
        return teacher;
    }


    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = baseMapper.selectList(null);
        System.out.println("初始化成功");
        return teachers;
    }


    @Override
    public boolean addTeacher(Teacher teacher) {
        // 检查是否存在相同的 teacher_id
        Teacher existingTeacher = teacherMapper.selectById(teacher.getTeacherId());
        if (existingTeacher != null) {
            // 如果存在相同的 teacher_id，返回 false 表示添加失败
            return false;
        }
        teacherMapper.insert(teacher);
        return true;
    }

    @Override
    public boolean updateTeacher(Teacher teacher) {
        return baseMapper.updateById(teacher) > 0;
    }
    /*@Override
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
    }*/

    @Override
    public boolean deleteTeacherById(String teacherId) {
        int result = teacherMapper.deleteById(teacherId);
        return result > 0;
    }

    @Override
    public List<Teacher> findTeachers(TeacherDTO filter) {
        return teacherMapper.selectTeachers(filter);
    }
}
