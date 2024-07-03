package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("inform")
public class Inform {

    @TableField("student_id")
    private String studentid;

    @TableField("content")
    private String content;

    @TableField("status")
    private String status;

    @TableField("inform_time")
    private LocalDateTime informtime;
}

