package com.kaifa.project.studentenrollmentsysytem.controller;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// 3/5
//http:/localhost:8088/login
@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:8083", allowCredentials = "true")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping   //over
    public Map<String, Object> login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        //获取一个session
        HttpSession session = request.getSession();
        // 设置会话Cookie
        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        response.addCookie(sessionCookie);
        sessionCookie.setPath("/");

        Map<String, Object> responses = new HashMap<>();
        Account account= accountService.getById(username);
        System.out.println("begin");
        if(account==null)
        {
            responses.put("status", "null");
            return responses;
        }
        else if(!account.password.equals(password))
        {
            responses.put("status", "password wrong");
            return responses;
        }
        else
        {
            String email=new String();
            String url =new String();
            if(account.identity.equals("teacher"))
            {
                Teacher teacher=teacherService.getById(username);
                email=teacher.getTemail();
                url=teacher.getFigureUrl();
            }
            else
            {
                Student student =studentService.getById(username);
                email=student.getEmail();
                url=student.getFigureUrl();
            }
            session.setAttribute("username", username);
            session.setAttribute("role", account.identity);

            System.out.println(session.getId());
            System.out.println(session.getAttribute("username"));

            responses.put("status", "success");
            responses.put("username",username);
            responses.put("role",account.identity);
            responses.put("accountName",account.accountNo);
            responses.put("email",email);
            responses.put("url",url);

        }
        return responses;
    }

    @PostMapping("activate")
    public Map<String, Object> activate(@RequestParam("studentId") String studentId,
                                        @RequestParam("username") String username,
                                        @RequestParam("idNumber") String idNumber,
                                        @RequestParam("password") String password,
                                        @RequestParam("email") String email,
                                        @RequestParam("code") String code  ){
        Student student =studentService.getById(studentId);
        Map<String, Object> response =new HashMap<>();
        for (Map.Entry<String, String> entry : verificationService.verificationCodes.entrySet()) {
            System.out.println("Email: " + entry.getKey() + ", Code: " + entry.getValue());
        }
        if(!verificationService.verifyCode(email,code))
        {
            response.put("status","code wrong");
            return response;
        }
        if(student==null)
        {
            response.put("status","id null");
            return response;
        }
        if(student.getIdNumber().equals(idNumber))
        {
            Account account=new Account(studentId,username,password,"student",email);
            accountService.save(account);
        }
        else
        {
            response.put("status","idNumber wrong");
            return response;
        }
        response.put("status","success");
        return response;
    }
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        // 销毁会话
        session.invalidate();
        Map<String, Object> response =new HashMap<>();
        response.put("status","success");
        return response;
    }

    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationService verificationService;
    @PostMapping("/sendVerificationCode")     //over
    public Map<String, Object> sendVerificationCode(@RequestParam String email) {
        String code = VerificationCodeGenerator.generateVerificationCode();
        emailService.sendVerificationCode(email, code);
        verificationService.saveVerificationCode(email, code);
        Map<String, Object> response =new HashMap<>();
        response.put("status","success");
        return response;
    }
//    @PostMapping("/verify")
//    public Map<String, Object> verify(@RequestParam String email,@RequestParam String code) {
//        Map<String, Object> response =new HashMap<>();
//        if(verificationService.verifyCode(email,code))
//        {
//            response.put("status","right");
//        }
//        else {
//            response.put("status", "wrong");
//        }
//        return response;
//    }

    @PostMapping("findpasswordback")  //over
    public Map<String, Object> findPasswordBack(@RequestParam String username,
                                                @RequestParam String email,
                                                @RequestParam String password,
                                                @RequestParam String code) {
        Map<String, Object> response =new HashMap<>();
        if(verificationService.verifyCode(email,code))
        {
            Account account=accountService.getById(username);
            account.setPassword(password);
            accountService.updateById(account);
            verificationService.removeVerificationCode(email,code);
            response.put("status","success");
        }
        else {
            response.put("status", "code wrong");
        }
        return response;
    }


}
