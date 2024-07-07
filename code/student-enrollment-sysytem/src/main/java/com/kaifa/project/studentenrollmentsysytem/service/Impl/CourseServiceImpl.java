package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.TeacherMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Course getCourseById(String courseId) {
        return courseMapper.selectById(courseId);
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
        // 通过 courseId 查询课程信息
        Course course = courseMapper.selectById(courseId);
        if (course != null) {
            // 从课程信息中获取 teacher_id
            String teacherId = course.getTeacherId();
            if (teacherId != null) {
                // 通过 teacher_id 查询教师信息
                Teacher teacher = teacherMapper.selectById(teacherId);
                if (teacher != null) {
                    List<TeacherDTO> teacherDTOs = new ArrayList<>();
                    teacherDTOs.add(new TeacherDTO(teacher));
                    return new CourseDTO(course, teacherDTOs);
                }
            }
        }
        return null;
    }


    @Override
    public CourseDTO getCourseDetailsByCourseName(String courseName) {
        System.out.println("Fetching course details for courseName: " + courseName); // 调试日志

        // 通过 courseName 查询所有匹配的课程
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", courseName);
        List<Course> courses = baseMapper.selectList(queryWrapper);

        if (!courses.isEmpty()) {
            System.out.println("Courses found: " + courses.size()); // 调试日志
            List<TeacherDTO> teacherDTOs = new ArrayList<>();
            for (Course course : courses) {
                // 从课程信息中获取 teacher_id
                String teacherId = course.getTeacherId();
                System.out.println("Checking teacherId: " + teacherId); // 调试日志

                if (teacherId != null) {
                    // 通过 teacher_id 查询教师信息
                    Teacher teacher = teacherMapper.selectById(teacherId);
                    if (teacher != null) {
                        System.out.println("Teacher found: " + teacher.getTeacherName()); // 调试日志
                        teacherDTOs.add(new TeacherDTO(teacher));
                    }
                }
            }
            // 返回第一个课程的详细信息和教师列表
            return new CourseDTO(courses.get(0), teacherDTOs);
        }
        System.out.println("No courses found for courseName: " + courseName); // 调试日志
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
    public List<Map<String, Object>> getLowestEnrollmentRateCourses() {
        List<Course> courses = courseMapper.getAllCourses();
        // 计算选课率并排序
        List<Map<String, Object>> sortedCourses = courses.stream()
                .map(course -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("courseName", course.getCourseName());
                    map.put("courseId", course.getCourseId());
                    map.put("teacherName", course.getTeacherName());
                    double enrollmentRate = (double) course.getCurrentNumOfStu() / course.getCeilingOfPersonnel();
                    map.put("enrollmentRate", enrollmentRate);
                    return map;
                })
                .sorted((c1, c2) -> Double.compare((double) c1.get("enrollmentRate"), (double) c2.get("enrollmentRate")))
                .limit(10)
                .collect(Collectors.toList());

        return sortedCourses;
    }

    @Override
    public void updateTeacherNameInCourse(String teacherId, String newTeacherName) {
        courseMapper.updateTeacherNameInCourse(teacherId, newTeacherName);
    }

    @Override
    public List<Course> getCoursesExcludingStudentAcademy(String studentId) {
        // 获取学生信息
        Student student = studentMapper.selectById(studentId);
        if (student == null) {
            System.out.println("学生ID " + studentId + " 不存在");
            return null;
        }
       String academy = student.getAcademy();
        System.out.println("学生ID: " + studentId + ", 学院: " + academy);

        // 使用QueryWrapper进行条件查询，排除学生所在学院的课程
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("course_id", academy);

        List<Course> nonMatchingCourses = baseMapper.selectList(queryWrapper);
        System.out.println("不匹配的课程数量: " + nonMatchingCourses.size());

        return nonMatchingCourses;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = baseMapper.selectList(null);
        System.out.println("初始化成功");
        return courses;
    }

    @Override
    public Map<String, Object> getCourseStatistics() {
        return courseMapper.getCourseStatistics();
    }

}
