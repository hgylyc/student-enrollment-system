package com.kaifa.project.studentenrollmentsysytem.pojo;

import lombok.Data;

@Data
public class TeacherDTO {
    private String teacherId;
    private String teacherName;
    private String tacademy;
    private String title;
    private String introduction;
    private String figureUrl;
    private String temail;


    public TeacherDTO(){}

    public TeacherDTO(Teacher teacher) {
        this.teacherId = teacher.getTeacherId();
        this.teacherName = teacher.getTeacherName();
        this.tacademy = teacher.getTacademy();
        this.title = teacher.getTitle();
        this.introduction = teacher.getIntroduction();
        this.figureUrl = teacher.getFigureUrl();
        this.temail = teacher.getTemail();
        String str = teacher.getTacademy();
    }





}
