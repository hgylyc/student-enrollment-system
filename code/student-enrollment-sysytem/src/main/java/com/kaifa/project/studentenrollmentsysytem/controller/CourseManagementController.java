package com.kaifa.project.studentenrollmentsysytem.controller;

import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.CourseTimeService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coursemanage")
public class CourseManagementController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseTimeService courseTimeService;

    @GetMapping   //实现初始化
    public List<CourseDTO> initialClasses(){
        List<Course> list =courseService.list();
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }

    @PostMapping("coursedetail")  //查询课程
    public Map<String, Object> courseDetail(@RequestParam("courseId") String courseId){
        Course course = courseService.getById(courseId);
        System.out.println(courseId);
        Map<String, Object> response = new HashMap<>();
        response.put("courseName", course.getCourseName());
        response.put("courseId", course.getCourseId());
        response.put("score", course.getScore());
        char ch = course.getCourseId().charAt(6);
        String str = Mapping.reverseMapsesemester(ch);
        response.put("semester", str);
        ch=course.getCourseId().charAt(0);
        str=Mapping.reverseMapCollege(ch);
        response.put("institution", str);
        response.put("introduction", course.getIntroduction());
        return response;
    }

    @PostMapping("coursecreate")    //创建课程  /over
    public Map<String, Object> courseCreate(HttpSession session,@RequestParam("courseName") String courseName,
                                            @RequestParam("teacherName") String teacherName,
                                            @RequestParam("teacherId") String teacherId,
                                            @RequestParam("ceilingOfPersonnel") int ceilingOfPersonnel,
                                            @RequestParam("courseType") String courseType,
                                            @RequestParam("score") String score,
                                            @RequestParam("semester") String semester,
                                            @RequestParam("institution") String institution,
                                            @RequestParam("identificationCode") String identificationCode,
                                            @RequestParam("introduction") String introduction,
                                            @RequestParam("time") Integer time,
                                            @RequestParam("status") String status,
                                            @RequestParam(value = "classRoomNo", required = false) String classRoomNo) {
        System.out.println(courseName);
        CourseCreate courseCreate = new CourseCreate();
        courseCreate.setCourseName(courseName);
        courseCreate.setStatus(status);
        courseCreate.setTeacherName(teacherName);
        courseCreate.setTeacherId(teacherId);
        courseCreate.setCeilingOfPersonnel(ceilingOfPersonnel);
        courseCreate.setCourseType(courseType);
        courseCreate.setScore(score);
        courseCreate.setSemester(semester);
        courseCreate.setInstitution(institution);
        courseCreate.setIdentificationCode(identificationCode);
        courseCreate.setIntroduction(introduction);
        courseCreate.setTime(time);
        courseCreate.setClassRoomNo(classRoomNo);
        Map<String, Object> response =new HashMap<>();
//        if (!session.getAttribute("role") .equals("teacher") ){
//            response.put("status","role wrong");
//            return response;
//        }
        //检验teacher字段合法性
        Teacher teacher = teacherService.getById(courseCreate.teacherId);
        if(teacher==null)
        {
            response.put("status","teacher not exist");
            return response;
        }
        else if(!teacher.getTeacherName().equals(courseCreate.getTeacherName()))
        {
            response.put("status","teacher name wrong");
            return response;
        }
        Course course=new Course(courseCreate);
        try {
            courseService.save(course);
            response.put("status", "success");
        } catch (DataIntegrityViolationException e) {
            response.put("status", "Duplicate entry for courseId");
        }

        // 同步更新 coursetime 表
        CourseTime courseTime = new CourseTime();
        courseTime.setCourseId(course.getCourseId());
        courseTime.setCourseName(course.getCourseName());
        courseTime.setClassroom(courseCreate.getClassRoomNo()); // 使用 createCourse 的教室号

        // 随机生成 coursetime 属性
        Random random = new Random();
        int dayOfWeek = random.nextInt(7) + 1; // 1-7 表示星期几
        int period = random.nextInt(6) + 1; // 1-6 表示第几节
        String courseTimeValue = "" + dayOfWeek + period;
        courseTime.setCourseTime(courseTimeValue);

        courseTimeService.save(courseTime);

        return response;
    }

    @PostMapping("coursedelete")    //删除课程
    public String courseDelete(HttpSession session,@RequestParam("courseId") String courseId){
        System.out.println(session.getAttribute("role"));
        if (!session.getAttribute("role") .equals( "teacher")){
            return "role wrong";
        }
        boolean isRemoved=courseService.removeById(courseId);
        if(isRemoved)
            return "success";
        else
            return "fail";
    }

    @PostMapping("courseselect")   //查询
    public List<CourseDTO> courseSelect(@RequestBody CourseDTO courseDTO){
        List<Course> list =courseService.findCourses(courseDTO);
        return list.stream().map(CourseDTO::new).collect(Collectors.toList());
    }
}
