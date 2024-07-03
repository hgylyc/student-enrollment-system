package com.kaifa.project.studentenrollmentsysytem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {
    @Autowired
    private DormitoryMapper dormitoryMapper;
    public Student login(String studentName,String password){
        return getOne(new QueryWrapper<Student>().eq("stduent_name",studentName).eq("password",password));
    }
    @Override
    public Student getStudentById(String studentId) {
        return getById(studentId);
    }
    @Override
    public List<Dormitory> getDormByAcGender(String academy, String gender) {
        LambdaQueryWrapper<Dormitory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dormitory::getAcademy, academy)
                .eq(Dormitory::getGender, gender);
        return dormitoryMapper.selectList(queryWrapper);
    }
}
