package com.kaifa.project.studentenrollmentsysytem.pojo;

import java.util.Random;
//生成六位随机押验证码

public class VerificationCodeGenerator {
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}