package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseTime;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import com.kaifa.project.studentenrollmentsysytem.service.CourseTimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private Student_courseService studentCourseService;
    @Autowired
    private CourseTimeService courseTimeService;
    @GetMapping
    public ResponseEntity<String[][]> generateSchedule(HttpSession session) {
        // 获取当前登录学生的 StudentId
        // String studentId = (String) session.getAttribute("username");
        String studentId = "20221443";
        // 初始化一个5*7的课程表数组
        String[][] schedule = new String[5][7];
        // 查询该学生已选择的课程
        List<Student_course> selectedCourses = studentCourseService.getStudentSchedule(studentId);
        // 填充课程表
        for (Student_course sc : selectedCourses) {
            // 根据课程ID获取课程时间信息
            CourseTime courseTime = courseTimeService.getCourseTimeByCourseId(sc.getCourseId());
            if (courseTime != null) {
                // 获取课程时间信息，这里假设 courseTime 是一个两位数字，例如 "31" 表示星期三的第一节课
                String courseTimeStr = courseTime.getCourseTime();
                // 解析 courseTime，确定课程在课程表中的位置
                if (courseTimeStr.length() == 2) {
                    int dayOfWeek = Character.getNumericValue(courseTimeStr.charAt(0)); // 星期几，直接使用
                    int sessionIndex = Character.getNumericValue(courseTimeStr.charAt(1)) - 1; // 第几节课，转换为数组索引
                    // 将课程号、课程名称、上课教室合并为一个字符串，并使用<br>标签换行
                    String courseInfo = courseTime.getCourseId() + "<br>" + courseTime.getCourseName() + "<br>" + courseTime.getClassroom();
                    // 填充课程表
                    if (sessionIndex >= 0 && sessionIndex < 5 && dayOfWeek >= 1 && dayOfWeek <= 7) {
                        schedule[sessionIndex][dayOfWeek - 1] = courseInfo; // 数组索引从0开始，减1
                    }
                }
            }
        }
        return ResponseEntity.ok().body(schedule);
    }
}