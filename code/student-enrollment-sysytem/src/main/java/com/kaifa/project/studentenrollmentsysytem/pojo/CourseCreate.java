package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class CourseCreate {
    public String courseName;   // 课程名
    public String teacherName; // 教师姓名
    public String teacherId; // 教师工号
    public int ceilingOfPersonnel; // 人数上限
    public String courseType; // 课程类型
    public String score; // 学分
    public String semester; //开课学期
    public String institution; //开课学院
    public String identificationCode;  //四位标识码
    public String introduction; // 课程简介
    public Integer time;//总学时

    public String status;

    public String classRoomNo;

    public String getClassRoomNo() {
        return classRoomNo;
    }

    public void setClassRoomNo(String classRoomNo) {
        this.classRoomNo = classRoomNo;
    }
}
