package com.kaifa.project.studentenrollmentsysytem.controller;
import com.kaifa.project.studentenrollmentsysytem.pojo.*;
import com.kaifa.project.studentenrollmentsysytem.service.AccountService;
import com.kaifa.project.studentenrollmentsysytem.service.EmailService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import com.kaifa.project.studentenrollmentsysytem.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


//http:/localhost:8088/login
@RestController
@RequestMapping("login")
@CrossOrigin(origins = "http://localhost:8086", allowCredentials = "true")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentService studentService;

    @PostMapping
    public Map<String, Object> login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam("username") String username,
                                     @RequestParam("password") String password) {
        HttpSession session = request.getSession(true);
        // 生成新会话ID
        request.changeSessionId();
        // 设置会话Cookie
        response.addCookie(new Cookie("JSESSIONID", session.getId()));

        System.out.println("work");
        Map<String, Object> responses = new HashMap<>();
        Account account= accountService.getById(username);
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
            session.setAttribute("username", username);
            session.setAttribute("role", account.identity);
            responses.put("status", "success");
            responses.put("username",username);
            responses.put("role",account.identity);
        }
        return responses;
    }

    @PostMapping("activate")
    public Map<String, Object> activate(@RequestParam("studentId") String studentId,
                                      @RequestParam("username") String username,
                                      @RequestParam("idNumber") String idNumber,
                                      @RequestParam("password") String password,
                                      @RequestParam("email") String email){
        Student student =studentService.getById(studentId);
        Map<String, Object> response =new HashMap<>();
        if(student==null)
        {
            response.put("status","id null");
            return response;
        }
        else if(student.getIdNumber().equals(idNumber))
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
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 销毁会话
        session.invalidate();
        return "redirect:/login";
    }

    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationService verificationService;
    @PostMapping("/sendVerificationCode")
    public Map<String, Object> sendVerificationCode(@RequestParam String email) {
        String code = VerificationCodeGenerator.generateVerificationCode();
        emailService.sendVerificationCode(email, code);
        verificationService.saveVerificationCode(email, code);
        Map<String, Object> response =new HashMap<>();
        response.put("status","success");
        return response;
    }

    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestParam String email,@RequestParam String code) {
        Map<String, Object> response =new HashMap<>();
        if(verificationService.verifyCode(email,code))
        {
            response.put("status","right");
        }
        else {
            response.put("status", "wrong");
        }
        return response;
    }
}
