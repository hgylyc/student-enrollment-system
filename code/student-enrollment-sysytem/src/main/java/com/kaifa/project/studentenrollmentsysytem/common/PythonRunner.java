package com.kaifa.project.studentenrollmentsysytem.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PythonRunner {
    public static List<Map<String, Object>> runPythonScript() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 调用Python脚本
            ProcessBuilder pb = new ProcessBuilder("python", "C:/Users/monster/Desktop/pro/student-enrollment-system-new/code/student-enrollment-sysytem/python/predict_report_count.py");

            // 设置工作目录为Spring Boot项目根目录
            pb.directory(new File(System.getProperty("user.dir")));

            Process p = pb.start();

            // 捕获标准输出
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                jsonOutput.append(line);
            }
            in.close();

            // 捕获标准错误
            BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = err.readLine()) != null) {
                errorOutput.append(line);
            }
            err.close();

            // 检查是否有错误输出
            if (errorOutput.length() > 0) {
                System.err.println("Python script error: " + errorOutput.toString());
                throw new RuntimeException("Python script error: " + errorOutput.toString());
            }

            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(jsonOutput.toString(), List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, Object> getTodayPrediction() {
        List<Map<String, Object>> predictions = runPythonScript();
        String today = LocalDate.now().toString();

        for (Map<String, Object> prediction : predictions) {
            if (prediction.get("date").equals(today)) {
                return prediction;
            }
        }
        return new HashMap<>();
    }
    }

