package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student_course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Student_courseMapper extends BaseMapper<Student_course> {

    @Select("SELECT * FROM student_course WHERE student_id = #{studentId}")
    List<Student_course> selectByStudentId(String studentId);
    public List<String> getSelectedCourseIdsByStudentId(String studentId);
}