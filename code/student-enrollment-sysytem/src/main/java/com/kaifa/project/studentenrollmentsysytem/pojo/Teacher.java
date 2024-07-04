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

    @TableField("title")
    private String title;

    @TableField("tacademy")
    private  String tacademy;

    @TableField("temail")
    private String temail;

    public String getTeacherName() {
        return teacherName;
    }
    public String getTeacherId() {
        return teacherId;
    }
    public String getIntroduction() {
        return introduction;
    }
    public String getFigureUrl() {
        return figureUrl;
    }
    public String getTitle() {
        return title;
    }
    public String getTacademy() {
        return tacademy;
    }
    public String getTemail() {
        return temail;
    }


    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public void setFigureUrl(String figureUrl) {
        this.figureUrl = figureUrl;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setTacademy(String tacademy) {
        this.tacademy = tacademy;
    }
    public void setTemail(String temail) {
        this.temail = temail;
    }
}