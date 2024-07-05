package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Student_courseService extends IService<Student_course> {
    boolean isCourseSelectByStu(String studentId,String courseId);
    public List<Student_course> getStudentSchedule(String studentId);
/*    List<Student_course> getCoursesByStudentId(String studentId);

    List<Student_course> getWeeklyCourseSchedule(String studentId);*/

}
