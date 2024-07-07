package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @SelectProvider(type = TeacherSqlBuilder.class, method = "buildSelectTeachers")
    List<Teacher> selectTeachers(@Param("filter") TeacherDTO filter);

    class TeacherSqlBuilder {
        public static String buildSelectTeachers(@Param("filter") final TeacherDTO filter) {
            return new SQL() {{
                SELECT("*");
                FROM("teacher");
                if (filter.getTeacherId() != null && !filter.getTeacherId().isEmpty()) {
                    WHERE("teacher_id = #{filter.teacherId}");
                }
                if (filter.getTeacherName() != null && !filter.getTeacherName().isEmpty()) {
                    WHERE("teacher_name LIKE #{filter.teacherName}");
                }
                if (filter.getTacademy() != null && !filter.getTacademy().isEmpty()) {
                    WHERE("tacademy = #{filter.tacademy}");
                }
                if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                    WHERE("title = #{filter.title}");
                }
                if (filter.getIntroduction() != null && !filter.getIntroduction().isEmpty()) {
                    WHERE("introduction = #{filter.introduction}");
                }
                if (filter.getFigureUrl() != null && !filter.getFigureUrl().isEmpty()) {
                    WHERE("figure_url = #{filter.figureUrl}");
                }
                if (filter.getTemail() != null && !filter.getTemail().isEmpty()) {
                    WHERE("temail = #{filter.temail}");
                }
            }}.toString();
        }
    }
}
