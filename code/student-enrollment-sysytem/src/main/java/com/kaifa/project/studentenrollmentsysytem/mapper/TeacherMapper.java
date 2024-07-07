package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @SelectProvider(type = TeacherSqlBuilder.class, method = "buildSelectTeachers")
    List<Teacher> selectFilteredTeachers(@Param("teacherDTO") TeacherDTO teacherDTO);

    @SelectProvider(type = TeacherSqlBuilder.class, method = "buildCountFilteredTeachers")
    int countFilteredTeachers(@Param("teacherDTO") TeacherDTO teacherDTO);

    @Select("SELECT COUNT(*) FROM teacher")
    int countAllTeachers();

    @Select("SELECT * FROM teacher")
    List<Teacher> selectTeacherFindAll();

    class TeacherSqlBuilder {
        public static String buildSelectTeachers(@Param("teacherDTO") final TeacherDTO teacherDTO) {
            return new SQL() {{
                SELECT("*");
                FROM("teacher");
                if (teacherDTO.getTeacherId() != null && !teacherDTO.getTeacherId().isEmpty()) {
                    WHERE("teacher_id = #{teacherDTO.teacherId}");
                }
                if (teacherDTO.getTeacherName() != null && !teacherDTO.getTeacherName().isEmpty()) {
                    WHERE("teacher_name LIKE #{teacherDTO.teacherName}");
                }
                if (teacherDTO.getTacademy() != null && !teacherDTO.getTacademy().isEmpty()) {
                    WHERE("tacademy = #{teacherDTO.tacademy}");
                }
                if (teacherDTO.getTitle() != null && !teacherDTO.getTitle().isEmpty()) {
                    WHERE("title = #{teacherDTO.title}");
                }
                if (teacherDTO.getIntroduction() != null && !teacherDTO.getIntroduction().isEmpty()) {
                    WHERE("introduction = #{teacherDTO.introduction}");
                }
                if (teacherDTO.getFigureUrl() != null && !teacherDTO.getFigureUrl().isEmpty()) {
                    WHERE("figure_url = #{teacherDTO.figureUrl}");
                }
                if (teacherDTO.getTemail() != null && !teacherDTO.getTemail().isEmpty()) {
                    WHERE("temail = #{teacherDTO.temail}");
                }
            }}.toString();
        }

        public static String buildCountFilteredTeachers(@Param("teacherDTO") final TeacherDTO teacherDTO) {
            return new SQL() {{
                SELECT("COUNT(*)");
                FROM("teacher");
                if (teacherDTO.getTeacherId() != null && !teacherDTO.getTeacherId().isEmpty()) {
                    WHERE("teacher_id = #{teacherDTO.teacherId}");
                }
                if (teacherDTO.getTeacherName() != null && !teacherDTO.getTeacherName().isEmpty()) {
                    WHERE("teacher_name LIKE #{teacherDTO.teacherName}");
                }
                if (teacherDTO.getTacademy() != null && !teacherDTO.getTacademy().isEmpty()) {
                    WHERE("tacademy = #{teacherDTO.tacademy}");
                }
                if (teacherDTO.getTitle() != null && !teacherDTO.getTitle().isEmpty()) {
                    WHERE("title = #{teacherDTO.title}");
                }
                if (teacherDTO.getIntroduction() != null && !teacherDTO.getIntroduction().isEmpty()) {
                    WHERE("introduction = #{teacherDTO.introduction}");
                }
                if (teacherDTO.getFigureUrl() != null && !teacherDTO.getFigureUrl().isEmpty()) {
                    WHERE("figure_url = #{teacherDTO.figureUrl}");
                }
                if (teacherDTO.getTemail() != null && !teacherDTO.getTemail().isEmpty()) {
                    WHERE("temail = #{teacherDTO.temail}");
                }
            }}.toString();
        }
    }
}
