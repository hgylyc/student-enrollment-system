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
    //学生的宿舍信息显示页面，负责显示可供学生选择的宿舍信息
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
    //学生申请宿舍
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
//学生的信息录入页面，负责录入除照片之外的信息
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
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            HttpSession session
    ) {
        // Check if any parameter is missing
        if (studentId == null) return Result.error("缺少必填参数: studentId", null);
        if (studentName == null) return Result.error("缺少必填参数: studentName", null);
        if (gender == null) return Result.error("缺少必填参数: gender", null);
        if (nativeSpace == null) return Result.error("缺少必填参数: nativeSpace", null);
        if (classNo == null) return Result.error("缺少必填参数: classNo", null);
        if (major == null) return Result.error("缺少必填参数: major", null);
        if (areaNo == null) return Result.error("缺少必填参数: areaNo", null);
        if (dormNo == null) return Result.error("缺少必填参数: dormNo", null);
        if (roomNo == null) return Result.error("缺少必填参数: roomNo", null);
        if (bedNo == null) return Result.error("缺少必填参数: bedNo", null);
        if (idNumber == null) return Result.error("缺少必填参数: idNumber", null);
        if (fatherName == null) return Result.error("缺少必填参数: fatherName", null);
        if (motherName == null) return Result.error("缺少必填参数: motherName", null);
        if (emergencyContactName == null) return Result.error("缺少必填参数: emergencyContactName", null);
        if (emergencyContactTel == null) return Result.error("缺少必填参数: emergencyContactTel", null);
        if (homeAddress == null) return Result.error("缺少必填参数: homeAddress", null);
        if (email == null) return Result.error("缺少必填参数: email", null);
        if (phoneNumber == null) return Result.error("缺少必填参数: phoneNumber", null);

        String studentid = (String) session.getAttribute("username");
        Student student = studentService.getStudentById(studentid);
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
        student.setPhoneNumber(phoneNumber);
        student.setState1(true);


        boolean updateResult = studentService.updateStudentInfo(student);
        if (updateResult) {
            return Result.success("更新成功", student);
        } else {
            return Result.error("更新失败", null);
        }
    }
    //学生申请校园卡
    @PostMapping("/applyCampusCard")
    public Result applyCampusCard(
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "studentId", required = false) String studentId,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "schoolCardPassword", required = false) String schoolCardPassword,
            @RequestParam(value = "confirmSchoolCardPassword", required = false) String confirmSchoolCardPassword,
            //@RequestParam(value = "captcha", required = false) String captcha,
            HttpSession session
    ) {
        // 检查所有必要的信息是否已经提供
        if (studentName == null || studentId == null || idNumber == null || schoolCardPassword == null || confirmSchoolCardPassword == null ) {
            return Result.error("请完成所有信息", null);
        }

        // 从session中获取验证码
        //String sessionCaptcha = (String) session.getAttribute("captcha");

        // 检查验证码
        //if (sessionCaptcha == null || !sessionCaptcha.equals(captcha)) {
          //  return Result.error("验证码错误，请重新输入", null);
       // }

        // 检查两次输入的校园卡密码是否一致
        if (!schoolCardPassword.equals(confirmSchoolCardPassword)) {
            return Result.error("两次输入的校园卡密码不一致，请重新设置密码", null);
        }

        // 获取学生信息并更新
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return Result.error("学生不存在", null);
        }
        student.setStudentName(studentName);
        student.setIdNumber(idNumber);
        student.setSchoolCardPassword(schoolCardPassword); // 更新校园卡密码
        student.setSchoolCardBalance(0);
        boolean updateResult = studentService.updateStudentInfo(student);
        if (updateResult) {
            return Result.success("校园卡申请成功", student);
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

    //缴费
    @PostMapping("payfee")
    public Result Paymoney(HttpSession session){
        String studentId = (String) session.getAttribute("username");
        // 如果studentId为空，返回空列表或者抛出异常
        if (studentId == null) {
            // 返回空列表或者抛出异常，根据需求选择
            return Result.error("用户id不存在，请重新登录",null);
        }
        Student student = studentService.getStudentById(studentId);
        student.setState2(true);
        boolean updateResult = studentService.updateStudentInfo(student);
        if (updateResult) {
            return Result.success("恭喜完成登录", student);
        } else {
            return Result.error("登录失败", null);
        }

    }


}
