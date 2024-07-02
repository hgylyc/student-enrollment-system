package com.kaifa.project.studentenrollmentsysytem.pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
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
