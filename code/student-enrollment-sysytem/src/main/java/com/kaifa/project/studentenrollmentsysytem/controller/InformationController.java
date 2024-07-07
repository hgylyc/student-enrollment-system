package com.kaifa.project.studentenrollmentsysytem.controller;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectRequest;
import com.kaifa.project.studentenrollmentsysytem.common.Result;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import com.kaifa.project.studentenrollmentsysytem.service.TeacherService;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

// 0/6
@RestController
@RequestMapping("information")
public class InformationController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private DormitoryService dormitoryService;

    @Autowired
    private TeacherService teacherService;

    private static final String CAPTCHA_SESSION_KEY = "random";

    @GetMapping("/captcha")
    public void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sRand = createRandomString(4);
        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_SESSION_KEY, sRand);

        response.setContentType("image/jpeg");
        setNoCacheHeaders(response);
        ServletOutputStream outputStream = response.getOutputStream();
        generateCaptchaImage(sRand, outputStream);
    }

    private String createRandomString(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    private void generateCaptchaImage(String captchaString, OutputStream outputStream) throws IOException {
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();

        graphics.setColor(getRandomColor(200, 250));
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        graphics.setColor(getRandomColor(160, 200));

        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x, y, x + xl, y + yl);
        }

        for (int i = 0; i < captchaString.length(); i++) {
            String rand = captchaString.substring(i, i + 1);
            graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            graphics.drawString(rand, 13 * i + 6, 16);
        }

        graphics.dispose();
        ImageIO.write(image, "JPEG", outputStream);
    }

    private Color getRandomColor(int lowerBound, int upperBound) {
        Random random = new Random();
        if (lowerBound > 255) lowerBound = 255;
        if (upperBound > 255) upperBound = 255;
        int r = lowerBound + random.nextInt(upperBound - lowerBound);
        int g = lowerBound + random.nextInt(upperBound - lowerBound);
        int b = lowerBound + random.nextInt(upperBound - lowerBound);
        return new Color(r, g, b);
    }

    private void setNoCacheHeaders(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
    }
    //学生的宿舍信息显示页面，负责显示可供学生选择的宿舍信息
    @PostMapping("dormitories")
    public Result dormitoriesList(HttpSession session, Model model) {
        String studentId = (String) session.getAttribute("username");
        // 如果 studentId 为空，返回空列表或者抛出异常
        if (studentId == null) {
            return Result.error("用户id不存在，请重新登录", null);
        }
        Student student = studentService.getStudentById(studentId);
        if(!(student.getAreaNo()==null)){
            return Result.error("学生已存在宿舍", null);
        }
        // 获取该专业和性别对应的所有宿舍
        String academy = student.getAcademy();
        String gender = student.getGender();
        List<DormitoryDTO> dormitoryDTOList = studentService.getDormByAcGender(academy, gender);
        model.addAttribute("dormitoryList", dormitoryDTOList);
        return Result.success("成功", dormitoryDTOList);
    }
    //学生申请宿舍
    @PostMapping("apply")
    public Result applyForDormitory(@RequestParam("areaNo") String areaNo,
                                    @RequestParam("dormNo") String dormNo,
                                    @RequestParam("roomNo") String roomNo,
                                    HttpSession session) {
        String studentId = (String) session.getAttribute("username");
        if (studentId == null) {
            return Result.error("用户未登录", null);
        }
        Student student = studentService.getStudentById(studentId);
        if (!(student.getDormNo() == null) && !student.getDormNo().isEmpty()) {
            return Result.error("学生已有宿舍", student.getDormNo());
        }
        Dormitory dormitory = dormitoryService.applyForDormitory(studentId, areaNo, dormNo, roomNo);
        if (dormitory == null) {
            return Result.error("宿舍申请失败", null);
        }
        student.setState2(true);
        studentService.updateStudent(student); // 更新学生信息
        return Result.success("宿舍申请成功", dormitory);
    }

    // 查看舍友
    @PostMapping("search")
    public Result findStudentsByDormitory(@RequestParam("areaNo") String areaNo,
                                          @RequestParam("dormNo") String dormNo,
                                          @RequestParam("roomNo") String roomNo) {
        List<Map<String, Object>> students = studentService.findStudentsByDormitory(areaNo, dormNo, roomNo);
        if (students.isEmpty()) {
            return Result.error("未找到匹配的学生信息", null);
        }
        return Result.success("查询成功", students);
    }


//学生的信息录入页面，负责录入除照片之外的信息
    @PostMapping("/updateStudent")
    public Result updateStudent(
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "nativeSpace", required = false) String nativeSpace,
            @RequestParam(value = "classNo", required = false) String classNo,
            @RequestParam(value = "academy", required = false) String academy,
            @RequestParam(value = "idNumber", required = false) String idNumber,
            @RequestParam(value = "fatherName", required = false) String fatherName,
            @RequestParam(value = "motherName", required = false) String motherName,
            @RequestParam(value = "emergencyContactName", required = false) String emergencyContactName,
            @RequestParam(value = "emergencyContactTel", required = false) String emergencyContactTel,
            @RequestParam(value = "homeAddress", required = false) String homeAddress,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "major", required = false) String major,
            @RequestParam(value = "captcha", required = false) String captcha,
            HttpSession session
    ) {
        // Check if any parameter is missing
        if (studentName == null) return Result.error("缺少必填参数: studentName", null);
        if (gender == null) return Result.error("缺少必填参数: gender", null);
        if (nativeSpace == null) return Result.error("缺少必填参数: nativeSpace", null);
        if (classNo == null) return Result.error("缺少必填参数: classNo", null);
        if (major == null) return Result.error("缺少必填参数: major", null);
        if (idNumber == null) return Result.error("缺少必填参数: idNumber", null);
        if (fatherName == null) return Result.error("缺少必填参数: fatherName", null);
        if (motherName == null) return Result.error("缺少必填参数: motherName", null);
        if (emergencyContactName == null) return Result.error("缺少必填参数: emergencyContactName", null);
        if (emergencyContactTel == null) return Result.error("缺少必填参数: emergencyContactTel", null);
        if (homeAddress == null) return Result.error("缺少必填参数: homeAddress", null);
        if (email == null) return Result.error("缺少必填参数: email", null);
        if (phoneNumber == null) return Result.error("缺少必填参数: phoneNumber", null);
        if (academy == null) return Result.error("缺少必填参数: academy", null);
        if (captcha == null) return Result.error("缺少必填参数: captcha", null);
        String sessionCaptcha = (String) session.getAttribute("random");
        if(!captcha.equals(sessionCaptcha)){
            return Result.error("验证码不正确",null);
        }
        Mapping mapping=new Mapping();
        String studentid = (String) session.getAttribute("username");
        Student student = studentService.getStudentById(studentid);
        student.setStudentName(studentName);
        student.setGender(gender);
        student.setNativeSpace(nativeSpace);
        student.setClassNo(classNo);
        student.setMajor(major);
        student.setIdNumber(idNumber);
        student.setFatherName(fatherName);
        student.setMotherName(motherName);
        student.setEmergencyContactName(emergencyContactName);
        student.setEmergencyContactTel(emergencyContactTel);
        student.setHomeAddress(homeAddress);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setAcademy(Character.toString(mapping.mapCollege(academy)));
        student.setState1(true);


        boolean updateResult = studentService.updateStudentInfo(student);
        if (updateResult) {
            return Result.success("sucess", student);
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
            @RequestParam(value = "captcha", required = false) String captcha,
            HttpSession session
    ) {
        // 检查所有必要的信息是否已经提供
        if (studentName == null || studentId == null || idNumber == null || schoolCardPassword == null || confirmSchoolCardPassword == null ) {
            return Result.error("请完成所有信息", null);
        }
        String sessionCaptcha = (String) session.getAttribute("random");
        if (captcha == null || !captcha.equals(sessionCaptcha)) {
            return Result.error("验证码错误，请重新输入", null);
        }

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

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;
    @PostMapping("upload")
    public Map<String, Object> upload(HttpSession session , @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String username =(String) session.getAttribute("username");
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }
        // 将MultipartFile转换为File
        File file = convertMultiPartToFile(multipartFile);
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        // 构造PutObject请求
        String fileName = multipartFile.getOriginalFilename();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        // 上传文件
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient
        ossClient.shutdown();
        // 保存文件的访问URL
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        Student student =studentService.getById(username);
        student.setFigureUrl(url);
        studentService.updateById(student);
        Map<String, Object> response =new HashMap<>();
        response.put("status","success");
        return response;
    }


    // 将MultipartFile转换为File
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @GetMapping("imageget")
    public ResponseEntity<byte[]> getImage(HttpSession session) throws IOException {
        String id = (String)session.getAttribute("username");
        String role=(String) session.getAttribute("role");
        String url =new String();
        if(role.equals("teacher"))
        {
            Teacher teacher=teacherService.getById(id);
            url=teacher.getFigureUrl();
        }
        else {
            Student student=studentService.getStudentById(id);
            url=student.getFigureUrl();
        }
        // 指定图片文件路径
        Path imagePath = Paths.get(url);
        // 读取图片文件为字节数组
        byte[] imageBytes = Files.readAllBytes(imagePath);
        // 动态检测文件的MIME类型
        String mimeType = Files.probeContentType(imagePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // 默认类型
        }
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mimeType));
        headers.setContentLength(imageBytes.length);
        return ResponseEntity.ok().headers(headers).body(imageBytes);
    }


    @PostMapping("pictest")
    public void pintest(HttpSession session , @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String username =(String) session.getAttribute("username");
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }
        // 将MultipartFile转换为File
        File file = convertMultiPartToFile(multipartFile);

        // 指定人脸检测的分类器文件路径
        String classifierPath = "C:/Users/monster/Desktop/haarcascade_frontalface_default.xml";

        // 加载分类器
        CascadeClassifier faceDetector = new CascadeClassifier(classifierPath);

        // 指定图像文件路径
        File imageFile = new File("path/to/your/image.jpg");
        // 读取图像文件
        Mat image = opencv_imgcodecs.imread(file.getAbsolutePath());
        // 进行人脸检测
        RectVector faces = new RectVector();
        faceDetector.detectMultiScale(image, faces);
        // 打印检测到的人脸数量
        System.out.println("检测到 " + faces.size() + " 张人脸");
    }

    @PostMapping("uploadtest")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("文件为空");
        }
        // 将MultipartFile转换为File
        File file = convertMultiPartToFile(multipartFile);
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        // 构造PutObject请求
        String fileName = multipartFile.getOriginalFilename();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        // 上传文件
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient
        ossClient.shutdown();
        // 保存文件的访问URL
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
        System.out.println(url);
        Map<String, Object> response =new HashMap<>();
        response.put("status","success");
        return response;
    }


}
