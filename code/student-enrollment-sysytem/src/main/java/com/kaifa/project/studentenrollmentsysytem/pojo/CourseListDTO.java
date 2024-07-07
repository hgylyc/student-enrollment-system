package com.kaifa.project.studentenrollmentsysytem.pojo;

public class CourseListDTO {

    private String courseId;
    private String courseName;
    private String score;
    private String courseType;
    private String status;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CourseListDTO(){}
    public CourseListDTO(Course course){
        this.courseName = course.getCourseName();
        this.courseId = course.getCourseId();
        this.score = course.getScore();
        this.courseType = course.getCourseType();
        this.status = course.getStatus();
    }
}
