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

    @Select("SELECT DATE(time_node) AS date_only, COUNT(*) FROM student GROUP BY date_only")
    List<Map<String, Object>> getTimeNode();

    @Select("SELECT state1,state2,state3 FROM student WHERE student_id = #{stuId}")
    List<Map<String, Object>> selectStateById(@Param("stuId") String stuId);
}
