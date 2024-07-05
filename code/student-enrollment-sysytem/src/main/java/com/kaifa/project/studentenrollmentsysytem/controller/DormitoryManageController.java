package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.DormitoryDTO;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("management")
public class DormitoryManageController {
    @Autowired
    private DormitoryService dormitoryService;
    @PostMapping ("searchDormitories")
    public Result searchDormitories(
            @RequestParam(required = false) String areaNo,
            @RequestParam(required = false) String dormNo,
            @RequestParam(required = false) String roomNo,
            @RequestParam(required = false) Integer isFull,
            @RequestParam(required = false) String academy,
            @RequestParam(required = false) String gender) {
        List<DormitoryDTO> dormitoryDTOList = dormitoryService.getDormitories(areaNo, dormNo, roomNo, isFull, academy, gender);
        return Result.success("查询成功", dormitoryDTOList);
    }
}
