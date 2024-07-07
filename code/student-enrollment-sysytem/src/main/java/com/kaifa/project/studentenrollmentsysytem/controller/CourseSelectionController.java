package com.kaifa.project.studentenrollmentsysytem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.CourseTimeService;
import com.kaifa.project.studentenrollmentsysytem.service.Student_courseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

// 0/8

@RestController
@RequestMapping("/CourseSel")
public class CourseSelectionController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private Student_courseService student_courseService;
    @Autowired
    private CourseTimeService courseTimeService;
    //初始化选课列表
    @GetMapping
    public List<Course> getAllCourses() {
        // 直接返回所有课程信息
        return courseService.getAllCourses();
    }
    //初始化选课列表
/*    @PostMapping
    public ResponseEntity<Map<String, Object>> getAllCourses(@RequestParam(defaultValue = "1") int currentPage,
                                                             @RequestParam(defaultValue = "10") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<Course> courses = courseService.getAllCourses();
        PageInfo<Course> pageInfo = new PageInfo<>(courses);

        Map<String, Object> response = new LinkedHashMap<>(); // 使用 LinkedHashMap 保持顺序
        response.put("total", pageInfo.getTotal());
        response.put("courses", pageInfo.getList());

        return ResponseEntity.ok(response);
    }*/
    // 筛选课程列表
    /*@GetMapping("/filter")
    public Map<String, List<CourseListDTO>> filterCourses(
            @RequestParam("courseType") String courseType,
            @RequestParam("courseName") String courseName,
            @RequestParam("courseId") String courseId *//*, HttpSession session*//*) {
        //String studentId = (String) session.getAttribute("username");
        String studentId = "20221443";
        Map<String, List<CourseListDTO>> result = new HashMap<>();
        result.putAll(getCourses(studentId, true, false, courseType, courseName, courseId));
        result.putAll(getCourses(studentId, true, true, courseType, courseName, courseId));
        return result;
    }
    private Map<String, List<CourseListDTO>> getCourses(String studentId, boolean filter, boolean excludeStudentAcademy, String... filters) {
        List<Course> courses;
        if (excludeStudentAcademy) {
            courses = courseService.getCoursesExcludingStudentAcademy(studentId);
        } else {
            courses = courseService.getCoursesByStudentAcademy(studentId);
        }

        if (filter && filters != null && filters.length == 3) {
            String courseType = filters[0];
            String courseName = filters[1];
            String courseId = filters[2];
            courses = courses.stream()
                    .filter(course -> (courseType.isEmpty() || course.getCourseType().equalsIgnoreCase(courseType)) &&
                            (courseName.isEmpty() || course.getCourseName().contains(courseName)) &&
                            (courseId.isEmpty() || course.getCourseId().contains(courseId)))
                    .collect(Collectors.toList());
        }
        Set<String> seenCourses = new HashSet<>();
        List<CourseListDTO> courseDTOs = courses.stream()
                .filter(course -> seenCourses.add(course.getCourseName())) // 仅保留第一次出现的课程名
                .map(course -> {
                    CourseListDTO courseDTO = new CourseListDTO();
                    courseDTO.setCourseId(course.getCourseId());
                    courseDTO.setCourseName(course.getCourseName());
                    courseDTO.setScore(course.getScore());
                    courseDTO.setCourseType(course.getCourseType());
                    // Determine status
                    boolean isFull = courseService.isCourseFull(course.getCourseId());
                    boolean isAlreadySelected = student_courseService.isCourseSelectByStu(studentId, course.getCourseId());
                    if (isFull) {
                        courseDTO.setStatus("已满");
                    } else if (isAlreadySelected) {
                        courseDTO.setStatus("已选");
                    } else {
                        courseDTO.setStatus("");
                    }
                    return courseDTO;
                })
                .collect(Collectors.toList());
        Map<String, List<CourseListDTO>> result = new HashMap<>();
        if (excludeStudentAcademy) {
            result.put("nonStudentAcademyCourses", courseDTOs);
        } else {
            result.put("studentAcademyCourses", courseDTOs);
        }
        return result;
    }*/
    // 选课确认
    @PostMapping("/selectCourse")
    public ResponseEntity<String> selectCourse(HttpSession session,@RequestParam String courseIds) {
        /*String studentId = "20221443";*/
        String studentId = (String) session.getAttribute("username");
        if (courseIds == null || courseIds.isEmpty()) {
            return ResponseEntity.badRequest().body("courseIds is required");
        }
        // 将逗号分隔的字符串转换为 List
        List<String> courseIdList = Arrays.asList(courseIds.split(","));
        // 获取学生已选课程的名称列表和对应的教师姓名
        List<CourseDTO> selectedCourses = student_courseService.getSelectedCourses(studentId);
        Map<String, String> selectedCourseNamesWithTeachers = selectedCourses.stream()
                .collect(Collectors.toMap(CourseDTO::getCourseName, CourseDTO::getTeacherName));
        // 获取学生已选课程的ID列表
        List<String> selectedCourseIds = selectedCourses.stream()
                .map(CourseDTO::getCourseId)
                .collect(Collectors.toList());
        // 根据已选课程ID列表获取课程时间列表
        List<CourseTime> selectedCourseTimes = courseTimeService.listByIds(selectedCourseIds);
        Map<String, String> selectedCourseTimesMap = selectedCourseTimes.stream()
                .collect(Collectors.toMap(CourseTime::getCourseId, CourseTime::getCourseTime));
        StringBuilder responseMessage = new StringBuilder();
        for (String courseId : courseIdList) {
            Course selectedCourse = courseService.getCourseById(courseId);
            if (selectedCourse == null) {
                responseMessage.append("Course ").append(courseId).append(" not found; ");
                continue;
            }
            String courseName = selectedCourse.getCourseName();
            boolean isFull = courseService.isCourseFull(courseId);
            boolean isAlreadySelected = student_courseService.isCourseSelectByStu(studentId, courseId);
            boolean isCourseNameSelected = selectedCourseNamesWithTeachers.containsKey(courseName);
            CourseTime newCourseTime = courseTimeService.getCourseTimeByCourseId(courseId);
            boolean isTimeConflict = selectedCourseTimesMap.values().stream()
                    .anyMatch(time -> timeConflict(time, newCourseTime.getCourseTime()));
            if (isFull) {
                responseMessage.append("Course ").append(courseId).append(" is already full; ");
            } else if (isAlreadySelected) {
                responseMessage.append("You have already selected course ").append(courseId).append("; ");
                System.out.println("选课重复1");
            } else if (isCourseNameSelected) {
                System.out.println("选课重复2");
                responseMessage.append("已选择过该课程，教师为：").append(selectedCourseNamesWithTeachers.get(courseName)).append(", ").append("不可重复选择。");
            } else if (isTimeConflict) {
                System.out.println("时间冲突");
                responseMessage.append("Course ").append(courseId).append(" conflicts with another selected course; ");
            } else {
                Student_course sc = new Student_course();
                sc.setStudentId(studentId);
                sc.setCourseId(courseId);
                student_courseService.save(sc);
                courseService.updateNumOfStu(courseId);
                responseMessage.append("Course ").append(courseId).append(" selected successfully; ");
            }
        }
        return ResponseEntity.ok(responseMessage.toString());
    }
    //时间冲突判断函数
    private boolean timeConflict(String time1, String time2) {
        return time1.equals(time2);
    }
    // 退课操作
    @PostMapping("/dropCourse")
    public String dropCourse(/*HttpSession session, */@RequestParam String courseId) {
        /*String studentId = (String) session.getAttribute("username");*/
        String studentId = "20221443";
        student_courseService.dropCourse(studentId, courseId);
        courseService.decreaseNumOfStu(courseId);
        return "Course dropped successfully";
    }
    // 查询课程
    @PostMapping("/searchCourse")
    public List<CourseDTO> searchCourse(@RequestParam String courseId,
                                        @RequestParam(required = false) String courseName,
                                        @RequestParam(required = false) String courseType) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseId(courseId);
        courseDTO.setCourseName(courseName);
        courseDTO.setCourseType(courseType);
        List<Course> list = courseService.findCourses(courseDTO);
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }
    // 获取课程详细信息及教师列表
    @PostMapping("/courseDetails")
    public CourseDTO getCourseDetailsByCourseName(@RequestParam("courseName") String courseName) {
        System.out.println("Received request for courseName: " + courseName); // 调试日志
        CourseDTO courseDetails = courseService.getCourseDetailsByCourseName(courseName);
        System.out.println("Course details fetched for courseName: " + courseName); // 调试日志
        return courseDetails;
    }
    // 获取已选课程列表
    @GetMapping("/selectedCourses")
    public List<CourseDTO> getSelectedCourses(/*HttpSession session*/) {
        String studentId = "20221443";
        List<CourseDTO> selectedCourses = student_courseService.getSelectedCourses(studentId);
        System.out.println("Selected courses (final): " + selectedCourses); // 调试日志，最终输出的课程数据
        return selectedCourses;
    }
}
