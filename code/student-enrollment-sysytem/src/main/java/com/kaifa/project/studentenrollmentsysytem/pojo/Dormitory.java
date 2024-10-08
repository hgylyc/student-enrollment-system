package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("dormitory")
@Data
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
    @TableField("academy")
    private String academy;
    @TableField("isFull")
    private Integer isFull;
    // 自动计算isFull属性
    public void setIsFull() {
        this.isFull = (this.currentnumofstu < this.maxnumofstu) ? 0 : 1;
    }
}
