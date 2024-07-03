package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import org.apache.ibatis.annotations.Select;

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

}