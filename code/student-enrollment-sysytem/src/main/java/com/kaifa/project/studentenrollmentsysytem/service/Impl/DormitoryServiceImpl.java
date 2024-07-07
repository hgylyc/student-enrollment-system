package com.kaifa.project.studentenrollmentsysytem.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.DormitoryDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Override
    public List<DormitoryDTO> getDormitories(String areaNo, String dormNo, String roomNo, Integer isFull, String academy, String gender) {
        List<Dormitory> dormitories = dormitoryMapper.selectDormitories(areaNo, dormNo, roomNo, isFull, academy, gender);
        return dormitories.stream()
                .map(studentService::convertToDTO)  // 使用 StudentService 的 convertToDTO 方法
                .collect(Collectors.toList());
    }
    public List<Map<String, Object>> myDormitory(String stuId){
        return dormitoryMapper.myDormitory(stuId);
    };
    //查找宿舍
    @Override
    public Dormitory getDormitory(String areano, String dormno, String roomno) {
        return dormitoryMapper.selectByDormitory(areano, dormno, roomno);
    }
    //删除宿舍
    @Override
    public boolean deleteDormitory(String areano, String dormno, String roomno) {
        int result = dormitoryMapper.deleteByDormitory(areano, dormno, roomno);
        return result > 0;
    }
}

