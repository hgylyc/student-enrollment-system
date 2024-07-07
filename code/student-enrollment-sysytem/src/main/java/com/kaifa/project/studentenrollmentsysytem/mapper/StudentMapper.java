package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
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

    @Select("SELECT student_id AS studentId, student_name AS studentName, native_space AS nativeSpace, academy AS academy, major AS major, class_no AS classNo FROM student WHERE area_no = #{areaNo} AND dorm_no = #{dormNo} AND room_no = #{roomNo}")
    List<Map<String, Object>> findStudentsByDormitory(@Param("areaNo") String areaNo, @Param("dormNo") String dormNo, @Param("roomNo") String roomNo);

    //这个方法用于统计在特定日期（date）内报道的学生人数
    @Select("SELECT COUNT(*) FROM student WHERE DATE(time_node) = #{date} AND time_node BETWEEN '2024-07-05 00:00:00' AND '2024-07-09 23:59:59'")
    int countByDate(String date);
   //这个默认方法用于查找今天报道的学生列表
    default List<Student> findReportedStudentsToday() {
        LocalDateTime startOfToday = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = startOfToday.plusDays(1).minusSeconds(1);

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("time_node", startOfToday, endOfToday);

        return this.selectList(queryWrapper);
    }
    //查找值定宿舍的学生
    @Select("SELECT student_id AS studentId, student_name AS studentName, native_space AS nativeSpace, academy, major, class_no AS classNo " +
            "FROM student " +
            "WHERE area_no = #{areano} AND dorm_no = #{dormno} AND room_no = #{roomno}")
    List<Map<String, Object>> findStusByDormitory(@Param("areano") String areano,
                                                      @Param("dormno") String dormno,
                                                      @Param("roomno") String roomno);
}