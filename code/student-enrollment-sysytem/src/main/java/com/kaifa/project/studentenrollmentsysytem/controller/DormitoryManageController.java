package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.DormitoryDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.pojo.studentManageDTO;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("management")
public class DormitoryManageController {
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    private StudentService studentService;
    @PostMapping ("searchDormitories")
    public Result searchDormitories(
            @RequestParam(required = false) String areaNo,
            @RequestParam(required = false) String dormNo,
            @RequestParam(required = false) String roomNo,
            @RequestParam(required = false) Integer isFull,
            @RequestParam(required = false) String academy,
            @RequestParam(required = false) String gender) {
        List<DormitoryDTO>dormitoryDTOList=new ArrayList<>();
        if(!(academy==null)&&!(academy.isEmpty())) {
            String Academy= Character.toString(Mapping.mapCollege(academy));
            System.out.println(Academy);
            dormitoryDTOList = dormitoryService.getDormitories(areaNo, dormNo, roomNo, isFull, Academy, gender);
        }
        else{
            dormitoryDTOList = dormitoryService.getDormitories(areaNo, dormNo, roomNo, isFull, academy, gender);
        }
        if(dormitoryDTOList==null)
            return Result.error("没有相应的宿舍，请重新查询",dormitoryDTOList);
        return Result.success("查询成功", dormitoryDTOList);
    }
    //宿舍的删除
    @PostMapping("/delete")
    public Result deleteDormitory(@RequestParam("areano") String areano,
                                  @RequestParam("dormno") String dormno,
                                  @RequestParam("roomno") String roomno) {
        Dormitory dormitory = dormitoryService.getDormitory(areano, dormno, roomno);
        if (dormitory == null) {
            return Result.error("未找到匹配的宿舍信息", null);
        }
        boolean deleteResult = dormitoryService.deleteDormitory(areano, dormno, roomno);
        if (deleteResult) {
            return Result.success("宿舍删除成功", null);
        } else {
            return Result.error("宿舍删除失败", null);
        }
    }
    //查找宿舍的同学
    @PostMapping("/findStusByDormitory")
    public Result findStusByDormitory(@RequestParam("areano") String areano,
                                          @RequestParam("dormno") String dormno,
                                          @RequestParam("roomno") String roomno) {
        List<Map<String, Object>> students = studentService.findStusByDormitory(areano, dormno, roomno);
        if (students.isEmpty()) {
            return Result.error("未找到匹配的学生信息", null);
        }
        return Result.success("查询成功", students);
    }
}
