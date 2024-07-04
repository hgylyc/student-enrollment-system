package com.kaifa.project.studentenrollmentsysytem.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService{
    @Autowired
    private DormitoryMapper dormitoryMapper;
    @Autowired
    private StudentService studentService;
    public Integer getMaxNumOfbed() {
        return dormitoryMapper.getMaxNumOfbed();
    }
    public Integer getLeftNumOfbed() {
        return dormitoryMapper.getLeftNumOfbed();
    }

    public List<Map<String, Object>> getStudentCountByArea() {
        return dormitoryMapper.getStudentCountByArea();
    }
    public Dormitory applyForDormitory(String studentId, String areano, String dormno, String roomno) {
        LambdaQueryWrapper<Dormitory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Dormitory::getAreano, areano)
                .eq(Dormitory::getDormno, dormno)
                .eq(Dormitory::getRoomno, roomno);

        Dormitory dormitory = baseMapper.selectOne(queryWrapper);
        if (dormitory == null || dormitory.getIsFull() == 1) {
            return null; // 宿舍不存在或已满
        }

        dormitory.setCurrentnumofstu(dormitory.getCurrentnumofstu() + 1);
        dormitory.setIsFull();
        dormitoryMapper.updateDormitory(dormitory);

        Student student = studentService.getStudentById(studentId);
        student.setAreaNo(dormitory.getAreano());
        student.setDormNo(dormitory.getDormno());
        student.setRoomNo(dormitory.getDormno());
        studentService.updateStudent(student);

        return dormitory;
    }

}

