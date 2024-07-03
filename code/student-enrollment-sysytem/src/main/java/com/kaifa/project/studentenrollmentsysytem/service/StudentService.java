package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public interface StudentService extends IService<Student> {
    Student getStudentById(String studentId);//通过id查找学生
    List<Dormitory> getDormByAcGender(String academy, String gender);
    List<Map<String, Object>> getNativeSpace();
    List<Map<String, Object>> getProcessState();
    List<Map<String, Object>> getTimeNode();

}
