package com.kaifa.project.studentenrollmentsysytem.pojo;

import lombok.Data;

@Data
public class DormitoryDTO {

    private String areano;

    private String dormno;

    private String roomno;

    private Integer maxnumofstu;

    private Integer currentnumofstu;

    private String gender;

    private String Academy;

    private Integer isFull;
}
