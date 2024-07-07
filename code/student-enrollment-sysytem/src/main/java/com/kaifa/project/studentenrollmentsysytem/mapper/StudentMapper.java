package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT native_space ,count(*) FROM student GROUP BY native_space")
    List<Map<String, Object>> getNativeSpace();

    @Select("SELECT count(*) ,sum(state1),sum(state2),sum(state3) from student")
    List<Map<String, Object>> getProcessState();

    @Select("SELECT DATE(time_node) AS date_only, COUNT(*) FROM student WHERE DATE(time_node) IS NOT NULL GROUP BY date_only")
    List<Map<String, Object>> getTimeNode();

    @Select("SELECT state1,state2,state3 FROM student WHERE student_id = #{stuId}")
    List<Map<String, Object>> selectStateById(@Param("stuId") String stuId);
    @Select("SELECT figure_url, email, phone_number, student_id, academy FROM student WHERE student_id = #{studentId}")
    Student getStudentInfoById(String studentId);
    //完成学生查询功能
    @Select("<script>" +
            "SELECT student_id AS studentId, student_name AS studentName, academy, state1, state2, state3 " +
            "FROM student " +
            "WHERE 1=1 " +
            "<if test='studentId != null and studentId != \"\"'> " +
            "AND student_id = #{studentId} " +
            "</if> " +
            "<if test='studentName != null and studentName != \"\"'> " +
            "AND student_name LIKE CONCAT('%', #{studentName}, '%') " +
            "</if> " +
            "<if test='academy != null and academy != \"\"'> " +
            "AND academy = #{academy} " +
            "</if> " +
            "</script>")
    List<Student> selectStudents(@Param("studentId") String studentId,
                                 @Param("studentName") String studentName,
                                 @Param("academy") String academy);
}

