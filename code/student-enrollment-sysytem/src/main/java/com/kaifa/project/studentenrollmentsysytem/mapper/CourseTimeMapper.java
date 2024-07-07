package com.kaifa.project.studentenrollmentsysytem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.CourseTime;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseTimeMapper extends BaseMapper<CourseTime> {
    CourseTime selectById(String id);
    public List<CourseTime> getCourseTimesByCourseIds(List<String> courseIds);
}