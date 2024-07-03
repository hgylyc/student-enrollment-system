package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class CourseDTO {
    private String courseId; // 课程ID
    private String courseName;   // 课程名
    private String institution;//开课部门
    private String status; // 选课状态
    private String teacherName; // 教师姓名
    private String time;//总课时
    private String score; // 学分
    private int currentNumOfStu; // 现有选课人数
    private int ceilingOfPersonnel; // 人数上限
    private boolean filled;//是否选满
    private String classRoomNo; // 教室号（非必要）


    public CourseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.courseName = course.getCourseName();
        this.teacherName = course.getTeacherName();
        this.ceilingOfPersonnel = course.getCeilingOfPersonnel();
        this.currentNumOfStu = course.getCurrentNumOfStu();
        this.classRoomNo = course.getClassRoomNo();
        this.score = course.getScore();
        this.status = course.getStatus();
        this.time = course.getTime();
        // 使用学院映射设置 institution
        String str = course.getCourseId();
        this.institution = Mapping.reverseMapCollege(str.charAt(0));
        // 计算 filled
        this.filled = this.currentNumOfStu >= this.ceilingOfPersonnel;
    }
}
