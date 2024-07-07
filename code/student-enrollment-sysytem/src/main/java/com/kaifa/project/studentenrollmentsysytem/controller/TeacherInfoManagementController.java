package com.kaifa.project.studentenrollmentsysytem.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaifa.project.studentenrollmentsysytem.pojo.Teacher;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDTO;
import com.kaifa.project.studentenrollmentsysytem.pojo.TeacherDetailsDTO;
import com.kaifa.project.studentenrollmentsysytem.service.CourseService;
import com.kaifa.project.studentenrollmentsysytem.service.ImgseService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/AdmInfo")
public class TeacherInfoManagementController {
    @Autowired
    private HttpSession session;
    @Autowired
    private CourseService courseService;
    @PostMapping
    public ResponseEntity<?> getAllTeachersForStu(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageHelper.startPage(currentPage, pageSize); // 启动分页
        List<Teacher> list = teacherService.getAllTeachers();
        PageInfo<Teacher> pageInfo = new PageInfo<>(list); // 获取分页信息
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("访问出错或无数据");
        } else {
            System.out.println("分页成功");
            Map<String, Object> response = new HashMap<>();
            response.put("teachers", pageInfo.getList());
            response.put("total", pageInfo.getTotal());
            return ResponseEntity.ok(response);
        }
    }
    @Autowired
    private TeacherService teacherService;
    //初始化管理端教师信息
    /*@GetMapping
    public List<Teacher> getAllTeachersForAdm() {
        return teacherService.getAllTeachers();
    }*/
    //管理端查询教师信息
/*    @GetMapping("/Asearch")
    public Teacher getTeacherByNameForAdm(@RequestParam String teacherName) {
        return teacherService.getTeacherByName(teacherName);
    }*/
    // 获取教师详细信息，包括照片
    @PostMapping("/teacherDetails")
    public TeacherDetailsDTO getTeacherDetails(@RequestParam String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        TeacherDetailsDTO teacherDetailsDTO = new TeacherDetailsDTO();
        teacherDetailsDTO.setTitle(teacher.getTitle());
        teacherDetailsDTO.setTeacherName(teacher.getTeacherName());
        teacherDetailsDTO.setTemail(teacher.getTemail());
        teacherDetailsDTO.setTacademy(teacher.getTacademy());
        teacherDetailsDTO.setIntroduction(teacher.getIntroduction());
        teacherDetailsDTO.setFigureUrl(teacher.getFigureUrl()); // 设置教师照片URL
        teacherDetailsDTO.setTeacherId(teacher.getTeacherId());
        /*String url =new String();
        url=teacher.getFigureUrl();
        Path imagePath = Paths.get(url);*/
        // 指定图片文件路径
        /*Path imagePath = Paths.get(url);
        // 读取图片文件为字节数组
        byte[] imageBytes = Files.readAllBytes(imagePath);*/
//        teacherDetailsDTO.setImageBytes(imageBytes);
        System.out.println("管理端详细信息成功"+teacherId);
        return teacherDetailsDTO;
    }
    //详细信息内编辑
    @PostMapping("/EditTeacherDetail")
    public String editTeacherDetail(@RequestParam("teacherId") String teacherId,
                                    @RequestParam(value = "tacademy", required = false) String tacademy,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "temail", required = false) String temail,
                                    @RequestParam(value = "introduction", required = false) String introduction,
                                    @RequestParam(value = "figureUrl", required = false) String figureUrl,
                                    @RequestParam(value = "teacherName", required = false) String teacherName) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            // 记录旧的教师名称
            String oldTeacherName = existingTeacher.getTeacherName();
            if (tacademy != null&&!tacademy.isEmpty()) existingTeacher.setTacademy(tacademy);
            if (title != null&&!title.isEmpty()) existingTeacher.setTitle(title);
            if (temail != null&&!temail.isEmpty()) existingTeacher.setTemail(temail);
            if (introduction != null&&!introduction.isEmpty()) existingTeacher.setIntroduction(introduction);
            if (figureUrl != null&&!figureUrl.isEmpty()) existingTeacher.setFigureUrl(figureUrl);
            if (teacherName != null&&!teacherName.isEmpty()) existingTeacher.setTeacherName(teacherName);
            boolean res = teacherService.updateTeacher(existingTeacher);
            if (res && teacherName != null && !teacherName.equals(oldTeacherName)) {

                courseService.updateTeacherNameInCourse(teacherId, teacherName);
            }
            return res ? "Teacher updated successfully" : "Failed to update teacher";
        } else {
            return "Teacher not found";
        }
    }
    @PostMapping("/Delteacher")
    public String deleteTeacher(@RequestParam String teacherId) {
        // 校验 teacherId 是否为空
        if (teacherId == null || teacherId.isEmpty()) {
            return "Teacher ID is required";
        }
        // 删除教师信息
        boolean res = teacherService.deleteTeacherById(teacherId);
        return res ? "Teacher deleted successfully" : "Failed to delete teacher";
    }
    //编辑
    @PutMapping("/EditTeacher")
    public String editTeacher(@RequestParam("teacherId") String teacherId,
                              @RequestParam(value = "teacherName", required = false) String teacherName,
                              @RequestParam(value = "tacademy", required = false) String tacademy,
                              @RequestParam(value = "title", required = false) String title) {
        Teacher existingTeacher = teacherService.getById(teacherId);
        if (existingTeacher != null) {
            if (teacherName != null) existingTeacher.setTeacherName(teacherName);
            if (tacademy != null) existingTeacher.setTacademy(tacademy);
            if (title != null) existingTeacher.setTitle(title);

            boolean res = teacherService.updateTeacher(existingTeacher);
            return res ? "Teacher updated successfully" : "Failed to update teacher";
        } else {
            return "Teacher not found";
        }
    }
    //创建教师
    @PostMapping("/Addteacher")
    public String addTeacher(@RequestParam("teacherId") String teacherId,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam(value ="introduction", required = false) String introduction,
                             @RequestParam(value ="figureUrl",required = false) String figureUrl,
                             @RequestParam("title") String title,
                             @RequestParam(value = "temail",required = false) String temail,
                             @RequestParam("tacademy") String tacademy
                             ) {
        Teacher t = new Teacher();
        t.setTacademy(tacademy);
        t.setTitle(title);
        t.setTemail(temail);
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        t.setFigureUrl(figureUrl);
        boolean res = teacherService.addTeacher(t);
        return res ? "Teacher added successfully" : "Failed to add teacher";
    }
    //筛选
    @PostMapping("/filterTeachers")
    public ResponseEntity<?> findTeachers(
            @RequestParam(required = false) String teacherId,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String tacademy,
            @RequestParam("currentPage") int currentPage,
            @RequestParam("pageSize") int pageSize) {

        // 如果 teacherName 不为空，则进行模糊查询处理
        if (teacherName != null && !teacherName.isEmpty()) {
            teacherName = "%" + teacherName + "%";
        }

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setTeacherId(teacherId);
        teacherDTO.setTeacherName(teacherName);
        teacherDTO.setTacademy(tacademy);

        // 启动分页
        PageHelper.startPage(currentPage, pageSize);
        List<Teacher> list = teacherService.findTeachers(teacherDTO, currentPage, pageSize);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);
        int total = teacherService.countFilteredTeachers(teacherDTO);
        System.out.println("total: " + total);

        Map<String, Object> response = new HashMap<>();
        response.put("teachers", pageInfo.getList().stream().map(TeacherDTO::new).collect(Collectors.toList()));
        response.put("total", String.valueOf(total)); // 将 total 转换为字符串

        return ResponseEntity.ok(response);
    }
    //照片上传
    @PostMapping("/Addteacher/uploadTeacherPhoto")
    public Map<String, Object> uploadTeacherPhoto(HttpSession session,@RequestParam(value = "file", required = false) MultipartFile file) {
        String username = (String) session.getAttribute("username");
        Map<String, Object> response = new HashMap<>();

        // 检查文件是否为空或者文件名是否为空
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty()) {
            response.put("status", "success");
            response.put("message", "No file uploaded, keeping the original file.");
            return response;
        }

        String UPLOAD_DIR = "C:/Users/Xia/Desktop/教师图片/";
        String fileName = file.getOriginalFilename();
        String url = UPLOAD_DIR + fileName;

        System.out.println(file.getName());
        System.out.println(url);

        try {
            // 创建目标文件
            File dest = new File(UPLOAD_DIR, fileName);
            // 保存文件到目标位置
            file.transferTo(dest);

            // 假设TeacherService包含获取和更新教师信息的方法
            Teacher teacher = teacherService.getTacherById(username);
            teacher.setFigureUrl(url);
            teacherService.updateById(teacher);

            response.put("status", "success");
            response.put("message", "File uploaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "fail");
            response.put("message", "Error: " + e.getMessage());
        }

        return response;
    }
    //创建教师
    /*@PostMapping("/Addteacher")
    public String addTeacher(@RequestParam("teacherId") String teacherId,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam(value ="introduction", required = false) String introduction,
                             @RequestParam(value ="figureUrl",required = false) String figureUrl,
                             @RequestParam("title") String title,
                             @RequestParam(value = "temail",required = false) String temail,
                             @RequestParam("tacademy") String tacademy,
                             @RequestParam(value = "file", required = false) MultipartFile file) {
        // 处理文件上传
        String UPLOAD_DIR = "C:/Users/Xia/Desktop/教师图片/";
        String url = null;

        if (file != null && !file.isEmpty()) {
            try {
                // 获取文件名
                String fileName = file.getOriginalFilename();
                // 创建目标文件
                File dest = new File(UPLOAD_DIR, fileName);
                // 保存文件到目标位置
                file.transferTo(dest);
                url = UPLOAD_DIR + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to upload photo: " + e.getMessage();
            }
        }

        // 创建教师对象并设置属性
        Teacher t = new Teacher();
        t.setTacademy(tacademy);
        t.setTitle(title);
        t.setTemail(temail);
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        if (url != null) {
            t.setFigureUrl(url);
        } else {
            t.setFigureUrl(figureUrl);
        }

        boolean res = teacherService.addTeacher(t);
        return res ? "Teacher added successfully" : "Failed to add teacher";
    }
*/
  /*  @Autowired
    private ImgseService imgseService;
    @PostMapping("/Addteacher")
    public String addTeacher(@RequestParam("teacherId") String teacherId,
                             @RequestParam("teacherName") String teacherName,
                             @RequestParam(value ="introduction", required = false) String introduction,
                             @RequestParam(value ="figureUrl", required = false) String figureUrl,
                             @RequestParam("title") String title,
                             @RequestParam(value = "temail", required = false) String temail,
                             @RequestParam("tacademy") String tacademy,
                             @RequestParam(value = "file", required = false) MultipartFile file) {
        // 处理文件上传
        String url = figureUrl;

        if (file != null && !file.isEmpty()) {
            try {
                url = imgseService.uploadFile(file);
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to upload photo: " + e.getMessage();
            }
        }
        // 创建教师对象并设置属性
        Teacher t = new Teacher();
        t.setTacademy(tacademy);
        t.setTitle(title);
        t.setTemail(temail);
        t.setTeacherId(teacherId);
        t.setTeacherName(teacherName);
        t.setIntroduction(introduction);
        t.setFigureUrl(url);

        System.out.println("Adding teacher with details:");
        System.out.println("ID: " + teacherId);
        System.out.println("Name: " + teacherName);
        System.out.println("Introduction: " + introduction);
        System.out.println("Figure URL: " + url);
        System.out.println("Title: " + title);
        System.out.println("Email: " + temail);
        System.out.println("Academy: " + tacademy);

        boolean res = teacherService.addTeacher(t);
        return res ? "Teacher added successfully" : "Failed to add teacher";
    }*/

}
