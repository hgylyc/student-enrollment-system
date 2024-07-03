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

    public Course(CourseCreate courseCreate){
        this.courseName=courseCreate.courseName;
        this.teacherName=courseCreate.teacherName;
        this.teacherId=courseCreate.teacherId;

        String str=null;
        switch (courseCreate.institution) {
            case "计算机学院":
                str+="C";
                break;
            case "理学院":
                str+="S";
                break;
            case "管理学院":
                str+="M";
                break;
            default:
                break;
        }

        switch (courseCreate.courseType) {
            case "必修课":
                str+="1";
                break;
            case "选修课":
                str+="2";
                break;
            case "实验课":
                str+="4";
                break;
            default:
                break;
        }

        str+=courseCreate.identificationCode;

        switch (courseCreate.semester) {
            case "大一春季学期":
                str+="A";
                break;
            case "大一秋季学期":
                str+="B";
                break;
            case "大二春季学期":
                str+="C";
                break;
            case "大二秋季学期":
                str+="D";
                break;
            case "大三春季学期":
                str+="E";
                break;
            case "大三秋季学期":
                str+="F";
                break;
            case "大四春季学期":
                str+="G";
                break;
            case "大四秋季学期":
                str+="H";
                break;
        }
        this.courseId=str;
        this.ceilingOfPersonnel=courseCreate.ceilingOfPersonnel;
        this.courseType=courseCreate.courseType;
        this.score=courseCreate.score;
        this.status="未开始";
        this.introduction=courseCreate.introduction ;
    }
}
