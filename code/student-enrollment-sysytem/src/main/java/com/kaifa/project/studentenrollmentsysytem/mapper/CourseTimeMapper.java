package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseTime;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseTimeMapper extends BaseMapper<CourseTime> {
    CourseTime selectById(String id);
}