package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.TeacherMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl <CourseMapper, Course> implements CourseService{
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<Course> getCoursesByStudentAcademy(String studentId) {
        // 获取学生信息
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            System.out.println("学生ID " + studentId + " 不存在");
            return new ArrayList<>();
        }

        String academy = student.getAcademy();
        System.out.println("学生ID: " + studentId + ", 学院: " + academy);

        // 使用QueryWrapper进行条件查询
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("course_id", academy);

        List<Course> matchingCourses = baseMapper.selectList(queryWrapper);
        System.out.println("匹配的课程数量: " + matchingCourses.size());

        return matchingCourses;
    }


    @Override
    public boolean isCourseFull(String courseId) {
        // 获取课程信息
        Course course = baseMapper.selectById(courseId);
        // 检查是否成功获取了课程对象
        if (course == null) {
            System.out.println("课程ID " + courseId + " 不存在");
            return false; // 或者根据需求返回其他值，表示课程未找到
        }
        // 检查课程人数信息是否为null
        Integer ceiling = course.getCeilingOfPersonnel();
        Integer currentNum = course.getCurrentNumOfStu();
        if (ceiling == null || currentNum == null) {
            System.out.println("课程ID " + courseId + " 的人数信息不完整");
            return false; // 或者根据需求返回其他值，表示课程信息不完整
        }
        // 进行人数比较
        return ceiling <= currentNum;
    }

    @Override
    public void updateNumOfStu(String courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("CourseId cannot be null");
        }

        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found for courseId: " + courseId);
        }

        course.setCurrentNumOfStu(course.getCurrentNumOfStu() + 1);
        baseMapper.updateById(course);
    }


    @Override
    public void decreaseNumOfStu(String courseId) {
        if(courseId == null){
            throw new IllegalArgumentException("CourseId cannot be null");
        }
        Course course = baseMapper.selectById(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found for courseId: " + courseId);
        }
        course.setCurrentNumOfStu(course.getCurrentNumOfStu() - 1);
        baseMapper.updateById(course);
    }

    @Override
    public List<Course> filterCoursesByCourseNameAndId(String courseName, String courseId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        if (courseName != null && !courseName.isEmpty()) {
            queryWrapper.like("course_name", courseName);
        }

        if (courseId != null && !courseId.isEmpty()) {
            queryWrapper.eq("course_id", courseId);
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Course> filterCoursesByType(String courseType) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if(courseType != null && !courseType.isEmpty()){
            queryWrapper.eq("course_type",courseType);
        }
        return baseMapper.selectList(queryWrapper);
    }
    @Override
    public List<Course> findCourses(CourseDTO filter) {
        return courseMapper.selectCourses(filter);
    }

    @Override
    public CourseDTO getCourseDetails(String courseId) {
        Course course = baseMapper.selectById(courseId);
        if (course != null) {
            // 获取教师信息列表
            List<Teacher> teachers = teacherMapper.selectList(new QueryWrapper<Teacher>().eq("course_id", courseId));
            List<TeacherDTO> teacherDTOs = teachers.stream().map(TeacherDTO::new).collect(Collectors.toList());
            return new CourseDTO(course, teacherDTOs);
        }
        return null;
    }


    public boolean updateCourse(Course course) {
        Course existingCourse = courseMapper.selectById(course.getCourseId());
        if (existingCourse != null) {
            boolean isUpdated = false;
            if (course.getStatus() != null) {
                existingCourse.setStatus(course.getStatus());
                isUpdated = true;
            }
            if (course.getTime() != null) {
                existingCourse.setTime(course.getTime());
                isUpdated = true;
            }
            if (course.getClassRoomNo() != null) {
                existingCourse.setClassRoomNo(course.getClassRoomNo());
                isUpdated = true;
            }
            if (isUpdated) {
                courseMapper.updateById(existingCourse);
                return true;
            }
        }
        return false;
    }



}
