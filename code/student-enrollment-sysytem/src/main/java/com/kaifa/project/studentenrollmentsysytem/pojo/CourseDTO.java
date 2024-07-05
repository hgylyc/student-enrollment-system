package com.kaifa.project.studentenrollmentsysytem.pojo;

import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {
    private String courseId; // 课程ID
    private String courseName;   // 课程名
    private String courseType; // 课程类型
    private String institution; // 开课部门
    private String status; // 选课状态
    private String teacherName; // 教师姓名
    private Integer time; // 总课时
    private String score; // 学分
    private int currentNumOfStu; // 现有选课人数
    private int ceilingOfPersonnel; // 人数上限
    private Boolean filled; // 是否选满
    private String classRoomNo; // 教室号（非必要）
    private String introduction; // 课程介绍（非必要）
    private List<TeacherDTO> teachers; // 教师信息列表

    public CourseDTO() {}

    public CourseDTO(Course course) {
        if (course != null) {
            this.courseType = course.getCourseType();
            this.courseId = course.getCourseId();
            this.courseName = course.getCourseName();
            this.teacherName = course.getTeacherName();
            this.ceilingOfPersonnel = course.getCeilingOfPersonnel();
            this.currentNumOfStu = course.getCurrentNumOfStu();
            this.classRoomNo = course.getClassRoomNo();
            this.score = course.getScore();
            this.status = course.getStatus();
            this.time = course.getTime();
            this.introduction = course.getIntroduction();
            // 使用学院映射设置 institution
            String str = course.getCourseId();
            if (str != null && !str.isEmpty()) {
                this.institution = Mapping.reverseMapCollege(str.charAt(0));
            }
            // 计算 filled
            this.filled = this.currentNumOfStu >= this.ceilingOfPersonnel;
        }
    }

    public CourseDTO(Course course, List<TeacherDTO> teachers) {
        this(course);
        this.teachers = teachers;
    }
}