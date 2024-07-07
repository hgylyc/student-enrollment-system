package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface InstituteMapper extends BaseMapper<Institute> {
    @Select("SELECT SUM(num_of_arrived_stu), SUM(num_of_student) FROM institute")
    List<Map<String, Object>>  getTotalNumOfArrivedStu();
    @Select("SELECT institute_name,num_of_student ,num_of_arrived_stu  FROM institute")
    List<Map<String, Object>> getStudentByInstitute();
    @Select("SELECT * FROM institute WHERE institute_name = #{instituteName}")
    Institute selectByName(@Param("instituteName")String instituteName);

}