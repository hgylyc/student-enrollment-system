package com.kaifa.project.studentenrollmentsysytem.controller;
import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping("information")
public class InformationController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;
    @PostMapping ("dormitories")
    public Result dormitoriesList (HttpSession session, Model model){
        String studentId = (String) session.getAttribute("username");
        // 如果studentId为空，返回空列表或者抛出异常
        if (studentId == null) {
            // 返回空列表或者抛出异常，根据需求选择
            return Result.error("用户id不存在，请重新登录",null);
        }
        Student student = studentService.getStudentById(studentId);
//        if ((student.getAreaNo()).equals("")) {
//            // 返回空列表或者抛出异常，根据需求选择
//            return Result.error("用户宿舍已存在",student.getAreaNo());
//        }
        // 获取该专业和性别对应的所有宿舍
            String academy = student.getAcademy();
            String gender = student.getGender();
            model.addAttribute("dormitoryList",studentService.getDormByAcGender(academy, gender));
            return Result.success("成功",studentService.getDormByAcGender(academy, gender));
    }
    @PostMapping("apply/{areano}/{dormno}/{roomno}")
    public Result applyForDormitory(@PathVariable String areano, @PathVariable String dormno, @PathVariable String roomno, HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        if (studentId == null) {
            return Result.error("用户未登录", null);
        }
        Student student = studentService.getStudentById(studentId);
        if(!(student.getDormNo().equals(""))){
            return Result.error("学生已有宿舍",studentId);
        }
        Dormitory dormitory = dormitoryService.applyForDormitory(studentId, areano, dormno, roomno);
        if (dormitory == null) {
            return Result.error("宿舍申请失败", null);
        }
        return Result.success("宿舍申请成功", dormitory);
    }

    @PostMapping("/updateStudent")
    public Result updateStudent(
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "nativeSpace", required = false) String nativeSpace,
            @RequestParam(value = "classNo", required = false) String classNo,
            @RequestParam(value = "major", required = false) String major,
            @RequestParam(value = "areaNo", required = false) String areaNo,
            @RequestParam(value = "dormNo", required = false) String dormNo,
            @RequestParam(value = "roomNo", required = false) String roomNo,
            @RequestParam(value = "bedNo", required = false) String bedNo,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "fatherName", required = false) String fatherName,
            @RequestParam(value = "motherName", required = false) String motherName,
            @RequestParam(value = "emergencyContactName", required = false) String emergencyContactName,
            @RequestParam(value = "emergencyContactTel", required = false) String emergencyContactTel,
            @RequestParam(value = "homeAddress", required = false) String homeAddress,
            @RequestParam(value = "email", required = false) String email
    ) {
        // Check if any parameter is missing
        if (studentId == null || studentName == null || gender == null || nativeSpace == null ||
                classNo == null || major == null || areaNo == null || dormNo == null ||
                roomNo == null || bedNo == null || idNumber == null || fatherName == null ||
                motherName == null || emergencyContactName == null || emergencyContactTel == null ||
                homeAddress == null || email == null) {
            return Result.error("缺少必填参数，请检查输入", null);
        }
        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        student.setGender(gender);
        student.setNativeSpace(nativeSpace);
        student.setClassNo(classNo);
        student.setMajor(major);
        student.setAreaNo(areaNo);
        student.setDormNo(dormNo);
        student.setRoomNo(roomNo);
        student.setBedNo(bedNo);
        student.setIdNumber(idNumber);
        student.setFatherName(fatherName);
        student.setMotherName(motherName);
        student.setEmergencyContactName(emergencyContactName);
        student.setEmergencyContactTel(emergencyContactTel);
        student.setHomeAddress(homeAddress);
        student.setEmail(email);

        boolean updateResult = studentService.updateStudentInfo(student);
        if (updateResult) {
            return Result.success("更新成功", student);
        } else {
            return Result.error("更新失败", null);
        }
    }

    @PostMapping("upload")
    public Map<String, Object> upload(HttpSession session , @RequestParam("file") MultipartFile file){
        String username = (String) session.getAttribute("username");
        Map<String, Object> response= new HashMap<>();;
        if (file.isEmpty()) {
            response.put("status", "file_null");
            return response;
        }
        String UPLOAD_DIR="E:/Temp/picture";
        String url=UPLOAD_DIR+file.getName();
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 创建目标文件
            File dest = new File(UPLOAD_DIR, fileName);
            // 保存文件到目标位置
            file.transferTo(dest);
            Student student = studentService.getStudentById(username);
            student.setFigureUrl(url);
            response.put("status", "success");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            response.put("status", "fail:"+e.getMessage());
            return response;
        }
    }

    @GetMapping("download")
    public ResponseEntity<Resource> downloadFile() {
        System.out.println("begin");
        String UPLOAD_DIR="E:/Temp/picture/a.png";
        try {
            // 构建文件路径
            Path filePath = Paths.get(UPLOAD_DIR).normalize();
            File file = filePath.toFile();
            String filename=file.getName();
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            // 读取文件内容
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
