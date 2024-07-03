package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @SelectProvider(type = CourseSqlBuilder.class, method = "buildSelectCourses")
    List<Course> selectCourses(@Param("filter") CourseDTO filter);

    class CourseSqlBuilder {
        public static String buildSelectCourses(@Param("filter") final CourseDTO filter) {
            String sql = new SQL() {{
                SELECT("*");
                FROM("course");
                if (filter.getCourseId() != null && !filter.getCourseId().isEmpty()) {
                    WHERE("course_id = #{filter.courseId}");
                }
                if (filter.getCourseName() != null && !filter.getCourseName().isEmpty()) {
                    WHERE("course_name = #{filter.courseName}");
                }
                if (filter.getInstitution() != null && !filter.getInstitution().isEmpty()) {
                    char ch = Mapping.mapCollege(filter.getInstitution());
                    String str = ch + "%";
                    WHERE(String.format("course_id LIKE '%s'", str));
                }
                if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                    WHERE("status = #{filter.status}");
                }
                if (filter.getTeacherName() != null && !filter.getTeacherName().isEmpty()) {
                    WHERE("teacher_name = #{filter.teacherName}");
                }
                if (filter.getTime() != null) {
                    WHERE("time = #{filter.time}");
                }
                if (filter.getScore() != null && !filter.getScore().isEmpty()) {
                    WHERE("score = #{filter.score}");
                }
                if (filter.getFilled() != null && filter.getFilled()) {
                    WHERE("ceiling_of_personnel < current_num_of_stu");
                }
            }}.toString();

            System.out.println("Generated SQL: " + sql);
            return sql;
        }
    }
}
