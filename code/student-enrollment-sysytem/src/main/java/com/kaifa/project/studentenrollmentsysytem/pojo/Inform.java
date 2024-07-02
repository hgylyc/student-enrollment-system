package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;

@Data
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

