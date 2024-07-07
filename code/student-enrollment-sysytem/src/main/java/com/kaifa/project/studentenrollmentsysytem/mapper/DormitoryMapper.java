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
        @Select("<script>" +
                "SELECT area_no AS areano, dorm_no AS dormno, room_no AS roomno, max_num_of_stu AS maxnumofstu, " +
                "current_num_of_stu AS currentnumofstu, gender, academy, isFull " +
                "FROM dormitory " +
                "WHERE 1=1 " +
                "<if test='areaNo != null and areaNo != \"\"'> " +
                "AND area_no = #{areaNo} " +
                "</if> " +
                "<if test='dormNo != null and dormNo != \"\"'> " +
                "AND dorm_no = #{dormNo} " +
                "</if> " +
                "<if test='roomNo != null and roomNo != \"\"'> " +
                "AND room_no = #{roomNo} " +
                "</if> " +
                "<if test='isFull != null'> " +
                "AND isFull = #{isFull} " +
                "</if> " +
                "<if test='academy != null and academy != \"\"'> " +
                "AND academy = #{academy} " +
                "</if> " +
                "<if test='gender != null and gender != \"\"'> " +
                "AND gender = #{gender} " +
                "</if> " +
                "</script>")
        List<Dormitory> selectDormitories(@Param("areaNo") String areaNo,
                                          @Param("dormNo") String dormNo,
                                          @Param("roomNo") String roomNo,
                                          @Param("isFull") Integer isFull,
                                          @Param("academy") String academy,
                                          @Param("gender") String gender);
        @Select("SELECT area_no, dorm_no, room_no, student_id, student_name, native_space, academy, major, class_no " +
                "FROM student " +
                "WHERE area_no = (SELECT area_no FROM student WHERE student_id = #{stuId}) " +
                "AND dorm_no = (SELECT dorm_no FROM student WHERE student_id = #{stuId}) " +
                "AND room_no = (SELECT room_no FROM student WHERE student_id = #{stuId})")
        List<Map<String, Object>> myDormitory(@Param("stuId") String stuId);
}