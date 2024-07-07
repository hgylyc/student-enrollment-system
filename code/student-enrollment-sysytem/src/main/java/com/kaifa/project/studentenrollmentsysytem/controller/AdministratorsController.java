package com.kaifa.project.studentenrollmentsysytem.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.InstituteService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import com.kaifa.project.studentenrollmentsysytem.common.PythonRunner;
import com.kaifa.project.studentenrollmentsysytem.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/administrator")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
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
    @Autowired
    private CourseService courseService;
    @CrossOrigin(origins = "http://localhost:9529")
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
        List<Map<String, Object>>  totalNumOfArrivedStu = instituteService.getTotalNumOfArrivedStu();
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
        //报道率最低的三个学院
        List<Map<String, Object>> lowestArrivalRateInstitutes = instituteService.getInstitutesWithLowestArrivalRate();
        response.put("lowestArrivalRateInstitutes", lowestArrivalRateInstitutes);

        // 获取选课率最低的10个课程
        List<Map<String, Object>> lowestEnrollmentRateCourses = courseService.getLowestEnrollmentRateCourses();
        response.put("lowestEnrollmentRateCourses", lowestEnrollmentRateCourses);
        //
        List<Map<String, Integer>> getDailyReportCount=studentService.getDailyReportCount();
        response.put("DailyReportCount",getDailyReportCount);

        Map<String, Integer> getTodayReportCount=studentService.getTodayReportCount();
        response.put("TodayCount",getTodayReportCount);
        //返回预测的人数
//        List<Map<String, Object>> dailyReportCount = PythonRunner.runPythonScript();
//        response.put("PredictCount", dailyReportCount);

        List<Map<String, Object>> dailyReportCount = PythonRunner.runPythonScript();
        response.put("DailypreditCount", dailyReportCount);
        //返回今天的预测人数
        Map<String, Object> todayReportCount = PythonRunner.getTodayPrediction();
        response.put("TodaypredictCount", todayReportCount);;
        return response;

    }

    @GetMapping("test")
    public Map<String, Object> test(){
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> studentCountByArea = dormitoryService.getStudentCountByArea();
        response.put("studentCountByArea", studentCountByArea);
        return response;
    }

    @GetMapping("/table1")
    public Map<String, Object> getData1() {
        Map<String, Object> response=new HashMap<>();
        List<Map<String, Object>> studentByInstitute = instituteService.getStudentByInstitute();
        response.put("studentByInstitute",studentByInstitute);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.valueToTree(response);
        JsonNode studentByInstituteNode = rootNode.get("studentByInstitute");
        // 提取num_of_arrived_stu字段
        List<Integer> numOfArrivedStuList = new ArrayList<>();
        for (JsonNode node : studentByInstituteNode) {
            numOfArrivedStuList.add(node.get("num_of_arrived_stu").asInt());
        }
        // 创建新的Map并放入提取的数据
        Map<String, Object> newResponse = new HashMap<>();
        newResponse.put("data", numOfArrivedStuList);

        return newResponse;
    }

    @GetMapping("/table2")
    public Map<String, Object> getData2() {
        Map<String, Object> responseBody = new HashMap<>();
        int[] arr = {24, 40, 101, 134, 90};
        responseBody.put("data", arr);
        return responseBody;
    }

    @GetMapping("/table3")
    public Map<String, Object> getData3() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> studentCountByArea = dormitoryService.getStudentCountByArea();
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> area : studentCountByArea) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", area.get("area_no")+"区入住人数");
            item.put("value", area.get("SUM(current_num_of_stu)"));
            data.add(item);
        }
        response.put("data", data);
        return response;
    }

    @GetMapping("/table4")
    public Map<String, Object> getData4() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        // 模拟数据
        int[] percentages = {60, 34, 50, 78, 10};
        String[] titles = {"计算机组成与设计", "数据库", "高数", "英语", "体育"};
        int[] values = {702, 354, 610, 793, 664};
        data.put("percentages", percentages);
        data.put("titles", titles);
        data.put("values", values);

        response.put("data", data);
        return response;
    }


    @GetMapping("/table5")
    public Map<String, Object> getData5() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("value", 100);
        item1.put("name", "已报道人数");
        Map<String, Object> item2 = new HashMap<>();
        item2.put("value", 200);
        item2.put("name", "未报道人数");
        data.add(item1);
        data.add(item2);
        response.put("data", data);
        return response;
    }

    @GetMapping("/table6")
    public Map<String, Object> getData() {
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("value", 100);
        item1.put("name", "你好");
        data.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("value", 26);
        item2.put("name", "北京");
        data.add(item2);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("value", 24);
        item3.put("name", "山东");
        data.add(item3);

        Map<String, Object> item4 = new HashMap<>();
        item4.put("value", 25);
        item4.put("name", "河北");
        data.add(item4);

        Map<String, Object> item5 = new HashMap<>();
        item5.put("value", 20);
        item5.put("name", "江苏");
        data.add(item5);

        Map<String, Object> item6 = new HashMap<>();
        item6.put("value", 25);
        item6.put("name", "浙江");
        data.add(item6);

        Map<String, Object> item7 = new HashMap<>();
        item7.put("value", 30);
        item7.put("name", "深圳");
        data.add(item7);

        Map<String, Object> item8 = new HashMap<>();
        item8.put("value", 42);
        item8.put("name", "广东");
        data.add(item8);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        return response;
    }

    @GetMapping("/num")
    public Map<String, Object> getNum() {

        Map<String, Object> response =new HashMap<>();
        response.put("reportedToday","312624");
        response.put("predictedToday","119910");
        return response;
    }

}
