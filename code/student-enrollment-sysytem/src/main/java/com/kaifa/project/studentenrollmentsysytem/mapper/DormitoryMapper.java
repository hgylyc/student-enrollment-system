package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
        @Select("SELECT SUM(max_num_of_stu) FROM dormitory")
        Integer getMaxNumOfbed();

        @Select("SELECT SUM((max_num_of_stu - current_num_of_stu)) FROM dormitory")
        Integer getLeftNumOfbed();

        @Select("SELECT area_no, SUM(current_num_of_stu)  FROM dormitory GROUP BY area_no")
        List<Map<String, Object>> getStudentCountByArea();
        @Update("UPDATE dormitory SET max_num_of_stu = #{dormitory.maxnumofstu}, current_num_of_stu = #{dormitory.currentnumofstu}, gender = #{dormitory.gender}, academy = #{dormitory.academy}, isFull = #{dormitory.isFull} WHERE area_no = #{dormitory.areano} AND dorm_no = #{dormitory.dormno} AND room_no = #{dormitory.roomno}")
        int updateDormitory(@Param("dormitory") Dormitory dormitory);

}