package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import org.apache.ibatis.annotations.Param;
import com.kaifa.project.studentenrollmentsysytem.pojo.DormitoryDTO;

import java.util.List;
import java.util.Map;


public interface DormitoryService extends IService<Dormitory> {
    Integer getMaxNumOfbed();
    Integer getLeftNumOfbed();
    List<Map<String, Object>> getStudentCountByArea();
    Dormitory applyForDormitory(String studentId, String areano, String dormno, String roomno);//申请宿舍
    List<Map<String, Object>> myDormitory(@Param("stuId") String stuId);
    List<DormitoryDTO> getDormitories(String areaNo, String dormNo, String roomNo, Integer isFull, String academy, String gender);
    Dormitory getDormitory(String areano, String dormno, String roomno);
    boolean deleteDormitory(String areano, String dormno, String roomno);
}