package com.kaifa.project.studentenrollmentsysytem.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("insitute")

public class Institute {
    @TableField("institute_name")
    private String institutename;
    @TableField("num_of_teacher")
    private Integer numofteacher;
    @TableField("num_of_student")
    private Integer numofstudent;
    @TableField("num_of_arrived_stu")
    private Integer numofarrivedstu;



}
