package com.kaifa.project.studentenrollmentsysytem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Course;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.mapper.StudentMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void testGetCoursesByStudentAcademy() {
        // 清空相关表的数据
        courseMapper.delete(new QueryWrapper<>());
        studentMapper.delete(new QueryWrapper<>());

        // 插入测试数据
        String studentId = "S001";
        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentName("Test Student");
        student.setGender("Male");
        student.setAcademy("C");
        studentMapper.insert(student);

        Course course1 = new Course();
        course1.setCourseId("C0001");
        course1.setCourseName("Course 1");
        course1.setCourseType("Core");
        courseMapper.insert(course1);

        Course course2 = new Course();
        course2.setCourseId("C0002");
        course2.setCourseName("Course 2");
        course2.setCourseType("Elective");
        courseMapper.insert(course2);

        // 调用被测试的方法
        List<Course> courses = courseService.getCoursesByStudentAcademy(studentId);
        courses.forEach(System.out::println);

        // 断言结果
        assertThat(courses).isNotEmpty();
        assertThat(courses).hasSize(2);
        assertThat(courses).extracting(Course::getCourseName).containsExactlyInAnyOrder("Course 1", "Course 2");
    }




    @Test
    public void testIsCourseFull() {
        Course course = new Course();
        course.setCourseId("C0001");
        course.setCurrentNumOfStu(30);
        course.setCeilingOfPersonnel(30);
        courseMapper.insert(course);

        boolean isFull = courseService.isCourseFull("C0001");
        assertThat(isFull).isTrue();
    }

    @Test
    public void testUpdateNumOfStu() {
        Course course = new Course();
        course.setCourseId("C0001");
        course.setCurrentNumOfStu(20);
        course.setCeilingOfPersonnel(30);
        courseMapper.insert(course);

        courseService.updateNumOfStu("C0001");

        Course updatedCourse = courseMapper.selectById("C0001");
        assertThat(updatedCourse.getCurrentNumOfStu()).isEqualTo(21);
    }

    @Test
    public void testFilterCoursesByCourseNameAndId() {
        Course course1 = new Course();
        course1.setCourseId("C0001");
        course1.setCourseName("Course 1");
        course1.setCourseType("Core");
        courseMapper.insert(course1);

        Course course2 = new Course();
        course2.setCourseId("C0002");
        course2.setCourseName("Course 2");
        course2.setCourseType("Elective");
        courseMapper.insert(course2);

        List<Course> coursesByName = courseService.filterCoursesByCourseNameAndId("Course 1", null);
        assertThat(coursesByName).isNotEmpty();
        assertThat(coursesByName.get(0).getCourseName()).isEqualTo("Course 1");

        List<Course> coursesById = courseService.filterCoursesByCourseNameAndId(null, "C0002");
        assertThat(coursesById).isNotEmpty();
        assertThat(coursesById.get(0).getCourseId()).isEqualTo("C0002");
    }

    @Test
    public void testFilterCoursesByType() {
        Course course1 = new Course();
        course1.setCourseId("C0001");
        course1.setCourseName("Course 1");
        course1.setCourseType("Core");
        courseMapper.insert(course1);

        Course course2 = new Course();
        course2.setCourseId("C0002");
        course2.setCourseName("Course 2");
        course2.setCourseType("Elective");
        courseMapper.insert(course2);

        List<Course> coreCourses = courseService.filterCoursesByType("Core");
        assertThat(coreCourses).isNotEmpty();
        assertThat(coreCourses).hasSize(1);
        assertThat(coreCourses.get(0).getCourseType()).isEqualTo("Core");

        List<Course> electiveCourses = courseService.filterCoursesByType("Elective");
        assertThat(electiveCourses).isNotEmpty();
        assertThat(electiveCourses).hasSize(1);
        assertThat(electiveCourses.get(0).getCourseType()).isEqualTo("Elective");
    }
}
