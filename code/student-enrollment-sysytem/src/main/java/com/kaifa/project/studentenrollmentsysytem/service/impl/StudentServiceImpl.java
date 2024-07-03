package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper,Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    public List<Map<String, Object>> getNativeSpace(){
        return studentMapper.getNativeSpace();
    };
    public List<Map<String, Object>> getProcessState(){
        return studentMapper.getProcessState();
    };
    public List<Map<String, Object>> getTimeNode(){
        return studentMapper.getTimeNode();
    };
}
