package com.kaifa.project.studentenrollmentsysytem.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class InstituteDTO {

    private String Institutename;

    private Integer numofteacher;

    private Integer numofstudent;

    private Integer numofarrivedstu;
}
