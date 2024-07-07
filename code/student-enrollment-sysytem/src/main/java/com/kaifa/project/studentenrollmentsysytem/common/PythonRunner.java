package com.kaifa.project.studentenrollmentsysytem.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PythonRunner {
    public static List<Map<String, Object>> runPythonScript() {
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            // 调用Python脚本
            ProcessBuilder pb = new ProcessBuilder("python", "D:/Dekstop/python/predict_report_count.py");
            Process p = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                jsonOutput.append(line);
            }
            in.close();

            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(jsonOutput.toString(), List.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
