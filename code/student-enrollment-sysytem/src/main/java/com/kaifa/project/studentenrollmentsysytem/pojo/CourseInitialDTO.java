package com.kaifa.project.studentenrollmentsysytem.pojo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CourseInitialDTO {
    private String courseId; // 课程ID
    private String courseName;   // 课程名

    private String courseType; // 课程类型
    private String institution; // 开课部门

    private String status; // 选课状态
    private String teacherName; // 教师姓名
    private Integer time; // 总课时
    private String score; // 学分
    private String semister;

    public CourseInitialDTO() {}

    public CourseInitialDTO(Course course) {
        if (course != null) {
            this.courseType = course.getCourseType();
            this.courseId = course.getCourseId();
            this.courseName = course.getCourseName();
            this.teacherName = course.getTeacherName();
            this.score = course.getScore();
            this.status = course.getStatus();
            this.time = course.getTime();
            // 使用学院映射设置 institution
            String str = course.getCourseId();
            if (str != null && !str.isEmpty()) {
                this.institution = Mapping.reverseMapCollege(str.charAt(0));
                this.semister= Mapping.reverseMapsesemester(str.charAt(6));
            }
        }
    }
}
