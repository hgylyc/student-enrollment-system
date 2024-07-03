package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dormitory")
public class Dormitory {
    @TableField("area_no")
    private String areano;
    @TableField("dorm_no")
    private String dormno;
    @TableField("room_no")
    private String roomno;
    @TableField("max_num_of_stu")
    private Integer maxnumofstu;
    @TableField("current_num_of_stu")
    private Integer currentnumofstu;
    @TableField("gender")
    private String gender;

}
