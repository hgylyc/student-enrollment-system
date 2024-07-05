package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.DormitoryDTO;

import java.util.List;
import java.util.Map;


public interface DormitoryService extends IService<Dormitory> {
    Integer getMaxNumOfbed();
    Integer getLeftNumOfbed();
    List<Map<String, Object>> getStudentCountByArea();
    Dormitory applyForDormitory(String studentId, String areano, String dormno, String roomno);//申请宿舍
    List<DormitoryDTO> getDormitories(String areaNo, String dormNo, String roomNo, Integer isFull, String academy, String gender);
}