package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableField("course_name")
    private String courseName;   // 课程名
    @TableId("course_id")
    private String courseId; // 课程ID
    @TableField("teacher_name")
    private String teacherName; // 教师姓名
    @TableField("teacher_id")
    private String teacherId; // 教师工号
    @TableField("ceiling_of_personnel")
    private int ceilingOfPersonnel; // 人数上限
    @TableField("current_num_of_stu")
    private int currentNumOfStu; // 现有选课人数
    @TableField("class_room_no")
    private String classRoomNo; // 教室号（非必要）
    @TableField("course_type")
    private String courseType; // 课程类型

    @TableField("score")
    private String score; // 学分

    @TableField("status")
    private String status; // 选课状态

    @TableField("introduction")
    private String introduction; // 课程简介
    private Integer time;//总课时
    public Course(){};

    public Course(CourseCreate courseCreate){
        this.courseName=courseCreate.courseName;
        this.teacherName=courseCreate.teacherName;
        this.teacherId=courseCreate.teacherId;
        String str="";
        char a=Mapping.mapCollege(courseCreate.institution);
        char b=Mapping.mapCourseType(courseCreate.courseType);
        char c=Mapping.mapSemester(courseCreate.semester);
        str+=a;
        str+=b;
        str+=courseCreate.identificationCode;
        str+=c;
        this.courseId=str;
        this.ceilingOfPersonnel=courseCreate.ceilingOfPersonnel;
        this.courseType=courseCreate.courseType;
        this.score=courseCreate.score;
        this.status="未开始";
        this.introduction=courseCreate.introduction ;
    }
}
