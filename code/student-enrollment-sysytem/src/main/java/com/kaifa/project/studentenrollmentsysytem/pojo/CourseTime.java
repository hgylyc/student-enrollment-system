package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("coursetime")
public class CourseTime {
    @TableField("coursename")
    private String courseName;
    @TableId("courseid")
    private String courseId;
    @TableField("coursetime")
    private String courseTime;
    @TableField("classroom")
    private String classroom;
}
