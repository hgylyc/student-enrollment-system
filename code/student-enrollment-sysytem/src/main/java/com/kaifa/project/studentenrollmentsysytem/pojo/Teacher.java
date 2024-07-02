package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    @TableField("teacher_name")
    private String teacherName;

    @TableId("teacher_id")
    private String teacherId;

    @TableField("introduction")
    private String introduction;

    @TableField("figure_url")
    private String figureUrl;
}
