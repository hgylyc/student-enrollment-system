package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    public String course_id;//课程id
    public String course_name;   //课程名
    public String teacher_name; //教师姓名
    public String teacher_id; //教师工号
    public int ceiling_of_personnel; //人数上限
    public String class_room_no; //教室号->非必要
    public String current_num_of_stu;//现有选课人数
    public String course_type;
    public String score; //学分
    public String status;//选课状态
    public String introduction; //课程简介
}
