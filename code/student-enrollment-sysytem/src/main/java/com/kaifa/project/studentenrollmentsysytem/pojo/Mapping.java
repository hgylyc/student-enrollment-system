package com.kaifa.project.studentenrollmentsysytem.pojo;

import java.util.HashMap;
import java.util.Map;

public class Mapping {
    // 定义一个静态映射表用于学院到字母的映射
    private static final Map<String, Character> collegeMap = new HashMap<>();

    // 定义一个静态映射表用于课程类型到数字的映射
    private static final Map<String, Character> courseTypeMap = new HashMap<>();

    // 定义一个静态映射表用于学期到字母的映射
    private static final Map<String, Character> semesterMap = new HashMap<>();

    // 反向映射表
    private static final Map<Character, String> reverseCollegeMap = new HashMap<>();
    // 静态代码块初始化映射表
    static {
        // 学院到字母的映射
        collegeMap.put("人文学院", 'H');
        collegeMap.put("工程学院", 'E');
        collegeMap.put("理学院", 'S');
        collegeMap.put("管理学院", 'M');
        collegeMap.put("医学院", 'B');
        collegeMap.put("计算机学院", 'C');
        collegeMap.put("外国语学院", 'L');
        collegeMap.put("法学院", 'J');
        collegeMap.put("教育学院", 'D');
        collegeMap.put("艺术学院", 'A');
        collegeMap.put("农学院", 'N');
        collegeMap.put("其他", 'O');

        // 课程类型到数字的映射
        courseTypeMap.put("必修课", '1');
        courseTypeMap.put("选修课", '2');
        courseTypeMap.put("公共课", '3');
        courseTypeMap.put("实验课", '4');
        courseTypeMap.put("毕业设计", '5');
        courseTypeMap.put("研讨课", '6');
        courseTypeMap.put("其他", '9');

        // 学期到字母的映射
        semesterMap.put("大一春季学期", 'A');
        semesterMap.put("大一秋季学期", 'B');
        semesterMap.put("大二春季学期", 'C');
        semesterMap.put("大二秋季学期", 'D');
        semesterMap.put("大三春季学期", 'E');
        semesterMap.put("大三秋季学期", 'F');
        semesterMap.put("大四春季学期", 'G');
        semesterMap.put("大四秋季学期", 'H');
        // 初始化反向映射表
        initializeReverseMap(collegeMap, reverseCollegeMap);
    }
    private static <K, V> void initializeReverseMap(Map<K, V> sourceMap, Map<V, K> reverseMap) {
        for (Map.Entry<K, V> entry : sourceMap.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
    }

    // 定义一个静态方法进行学院映射
    public static char mapCollege(String collegeName) {
        return collegeMap.getOrDefault(collegeName, '?');
    }

    // 定义一个静态方法进行课程类型映射
    public static char mapCourseType(String courseTypeName) {
        return courseTypeMap.getOrDefault(courseTypeName, '?');
    }

    public static char mapSemester(String semesterName) {
        return semesterMap.getOrDefault(semesterName, '?');
    }

    // 定义静态方法进行反向学院映射
    public static String reverseMapCollege(char code) {
        return reverseCollegeMap.getOrDefault(code, "未知学院");
    }
}
