package com.kaifa.project.studentenrollmentsysytem.pojo;

import lombok.Data;

@Data
public class StudentDTO {
    private String studentName;
    private String studentId;
    private String academy;
    private String phoneNumber;
    private String email;
    private byte[] image;
    public StudentDTO(String email, String phoneNumber, String studentId, String academy, byte[] image) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.academy = academy;
        this.image = image;
    }
}
