package com.kaifa.project.studentenrollmentsysytem.pojo;

public class TeacherDetailsDTO {
    private String teacherName;
    private String temail;
    private String tacademy;
    private String introduction;
    private String title;

    // Getters and Setters
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTemail() {
        return temail;
    }

    public void setTemail(String temail) {
        this.temail = temail;
    }

    public String getTacademy() {
        return tacademy;
    }

    public void setTacademy(String tacademy) {
        this.tacademy = tacademy;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TeacherDetailsDTO(){}
    public TeacherDetailsDTO(Teacher teacher){
        this.teacherName = teacher.getTeacherName();
        this.tacademy = teacher.getTacademy();
        this.temail = teacher.getTemail();
        this.introduction = teacher.getIntroduction();
    }
}
