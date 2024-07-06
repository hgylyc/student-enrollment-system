package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
@TableName("student")
@Data
public class Student {
    @TableField("student_name")
    private String studentName;
    private String gender;
    @TableField("native_space")
    private String nativeSpace;
    @TableId("student_id")
    private String studentId;

    @TableField("class_no")
    private String classNo;
    private String major;
    @TableField("area_no")
    private String areaNo;
    @TableField("dorm_no")
    private String dormNo;
    @TableField("room_no")
    private String roomNo;
    @TableField("bed_no")
    private String bedNo;
    private boolean state1;
    private boolean state2;
    private boolean state3;
    @TableField("time_node")
    private LocalDateTime timeNode;//进入阶段的时间
    @TableField("figure_url")
    private String figureUrl;
    @TableField("id_number")
    private String idNumber;
    @TableField("father_name")
    private String fatherName;
    @TableField("mother_name")
    private String motherName;
    @TableField("emergency_contact_name")
    private String emergencyContactName;
    @TableField("emergency_contact_tel")
    private String emergencyContactTel;
    @TableField("home_address")
    private String homeAddress;
    private String email;
    @TableField("school_card_password")
    private String schoolCardPassword;
    @TableField("school_card_balance")
    private Integer schoolCardBalance;
    @TableField("academy")
    private String academy;
    @TableField("phone_number")
    private String phoneNumber;


}
