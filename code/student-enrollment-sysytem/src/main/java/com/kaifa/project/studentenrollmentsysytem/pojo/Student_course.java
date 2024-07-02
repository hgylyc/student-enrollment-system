package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("student_course")
public class Student_course {
    @TableField("course_id")
    private String courseid;
    @TableField("student_id")
    private String studentid;
}
