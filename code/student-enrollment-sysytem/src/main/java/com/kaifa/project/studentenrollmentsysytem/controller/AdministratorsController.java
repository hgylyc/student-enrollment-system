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
import java.math.BigDecimal;
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
        //获取今日报道人数
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

    @GetMapping("/table1")
    public Map<String, Object> getData1() {
        Map<String, Object> response=new HashMap<>();
        List<Map<String, Object>> studentByInstitute = instituteService.getStudentByInstitute();
        response.put("studentByInstitute", studentByInstitute);
        // 使用ObjectMapper将Map转换为JsonNode
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
        List<Map<String, Object>> predictCount = PythonRunner.runPythonScript();
        // 提取predicted_count字段并存储在int[]数组中
        int[] predictedCounts = new int[predictCount.size()];
        for (int i = 0; i < predictCount.size(); i++) {
            predictedCounts[i] = ((Number) predictCount.get(i).get("predicted_count")).intValue();
        }

        // 创建responseBody并放入数据
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", predictedCounts);

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

    //使用 convertToInt 方法从Map中提取每个值，并将其转换为 int 类型的变量。
    private int convertToInt(Object value) {
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).intValue();
        } else if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            throw new IllegalArgumentException("Value is not a number: " + value);
        }
    }

    @GetMapping("/table4")
    public Map<String, Object> getData4() {
        Map<String, Object> datas =courseService.getCourseStatistics();

        // 提取并转换为整数变量
        int finishedCourses = convertToInt(datas.get("finishedCourses"));
        int ongoingCourses = convertToInt(datas.get("ongoingCourses"));
        int fullCourses = convertToInt(datas.get("fullCourses"));
        int totalCourses = convertToInt(datas.get("totalCourses"));
        int notStartedCourses = convertToInt(datas.get("notStartedCourses"));

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        // 模拟数据
        int[] percentages = {notStartedCourses*100/totalCourses, ongoingCourses*100/totalCourses,
                finishedCourses*100/totalCourses, fullCourses*100/totalCourses};
        String[] titles = {"未开始选课", "正在选课", "选课结束", "容量已满"};
        int[] values = {totalCourses , totalCourses , totalCourses , totalCourses};
        data.put("percentages", percentages);
        data.put("titles", titles);
        data.put("values", values);

        response.put("data", data);
        return response;
    }


    @GetMapping("/table5")
    public Map<String, Object> getData5() {
        List<Map<String, Object>> totalNumOfArrivedStu = instituteService.getTotalNumOfArrivedStu();

        // 提取并计算数据
        int totalArrived = 0;
        int totalStudents = 0;

        if (!totalNumOfArrivedStu.isEmpty()) {
            Map<String, Object> totals = totalNumOfArrivedStu.get(0);
            totalArrived = ((Number) totals.get("SUM(num_of_arrived_stu)")).intValue();
            totalStudents = ((Number) totals.get("SUM(num_of_student)")).intValue();
        }

        int totalNotArrived = totalStudents - totalArrived;

        // 将数据转换为所需格式
        List<Map<String, Object>> data = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("value", totalArrived);
        item1.put("name", "已报道人数");
        data.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("value", totalNotArrived);
        item2.put("name", "未报道人数");
        data.add(item2);

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        return response;
    }

    @GetMapping("/table6")
    public Map<String, Object> getData6() {
        List<Map<String, Object>> NativeSpace = studentService.getNativeSpace();

        // 将数据按 count(*) 字段降序排序
        NativeSpace.sort((a, b) -> ((Number) b.get("count(*)")).intValue() - ((Number) a.get("count(*)")).intValue());

        // 只取前六条数据
        List<Map<String, Object>> topSixData = NativeSpace.subList(0, Math.min(NativeSpace.size(), 6));

        // 将数据转换为所需格式
        List<Map<String, Object>> data = new ArrayList<>();
        for (Map<String, Object> entry : topSixData) {
            Map<String, Object> item = new HashMap<>();
            item.put("value", entry.get("count(*)"));
            item.put("name", entry.get("native_space"));
            data.add(item);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        return response;
    }

    @GetMapping("/num")
    public Map<String, Object> getNum() {
        Map<String, Integer> getTodayReportCount=studentService.getTodayReportCount();
        Map.Entry<String, Integer> entry = getTodayReportCount.entrySet().iterator().next();
        int count = entry.getValue();
        String reportedToday = Integer.toString(count);

        Map<String, Object> todayReportCount = PythonRunner.getTodayPrediction();
        String predictedToday =todayReportCount.get("predicted_count").toString();
        Map<String, Object> response =new HashMap<>();
        response.put("reportedToday",reportedToday);
        response.put("predictedToday",predictedToday);
        return response;
    }

}
