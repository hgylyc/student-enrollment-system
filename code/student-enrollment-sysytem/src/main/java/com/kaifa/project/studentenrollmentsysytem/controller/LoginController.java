package com.kaifa.project.studentenrollmentsysytem.controller;
import com.baomidou.mybatisplus.annotation.TableField;
import com.kaifa.project.studentenrollmentsysytem.mapper.AccountMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Account;
import com.kaifa.project.studentenrollmentsysytem.pojo.Student;
import com.kaifa.project.studentenrollmentsysytem.service.AccountService;
import com.kaifa.project.studentenrollmentsysytem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;


//http:/localhost:8088/login
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private StudentService studentService;

    @PostMapping
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        Account account= accountService.getById(username);
        if(account==null)
            return "null";
        else if(!account.password.equals(password))
            return "password wrong";
        else
        {
            session.setAttribute("username", username);
            session.setAttribute("role", account.identity);
        }
        return "success";
    }

    @PostMapping("activate")
    public String activat(@RequestParam("studentId") String studentId,
                          @RequestParam("username") String username,
                          @RequestParam("idNumber") String idNumber,
                          @RequestParam("password") String password,
                          @RequestParam("email") String email){
        Student student =studentService.getById(studentId);
        if(student==null)
            return "null";
        //return student.getIdNumber()+"=" +idNumber;
        else if(student.getIdNumber().equals(idNumber))
        {
            Account account=new Account(studentId,username,password,"student",email);
            accountService.save(account);
        }
        else
        {
            return "idNumber wrong";
        }
        return "success";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // 销毁会话
        session.invalidate();
        return "redirect:/login";
    }
}
