package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.InstituteService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/administrator")
public class AdministratorsController {
    //今日报道人数，报道开始天数，在线管理员
    //报道人数浮动

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;
    @Autowired
    private InstituteService instituteService;

    @GetMapping("/data")
    public Map<String, Object> data() {

        Map<String, Object> response = new HashMap<>();
        //报道人数浮动
        List<Map<String, Object>> timeNode = studentService.getTimeNode();
        response.put("timeNode",timeNode);
        //报道环节完成人数，state1,state2,state2
        List<Map<String, Object>> processState = studentService.getProcessState();
        response.put("processState",processState);
        //生源地分布
        List<Map<String, Object>> NativeSpace = studentService.getNativeSpace();
        response.put("NativeSpace",NativeSpace);
        //总报道人数
        Integer totalNumOfArrivedStu = instituteService.getTotalNumOfArrivedStu();
        response.put("totalNumOfArrivedStu", totalNumOfArrivedStu);
        //学院报道情况分布
        List<Map<String, Object>> studentByInstitute = instituteService.getStudentByInstitute();
        response.put("studentByInstitute",studentByInstitute);
        //总宿舍床位
        Integer maxNumOfbed = dormitoryService.getMaxNumOfbed();
        response.put("maxNumOfbed", maxNumOfbed);
        //剩余宿舍床位
        Integer leftNumOfbed = dormitoryService.getLeftNumOfbed();
        response.put("leftNumOfbed", leftNumOfbed);
        //入住情况园区分布
        List<Map<String, Object>> studentCountByArea = dormitoryService.getStudentCountByArea();
        response.put("studentCountByArea", studentCountByArea);
        return response;

    }
}