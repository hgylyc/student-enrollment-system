package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jsonwebtoken.Jwts;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId("id_number")
    public String id;//学号
    @TableField("account_no")
    public String accountNo;
    public String password;
    public String identity;
    public String email;

    public Account(String id, String username, String password, String role, String email) {
        this.id = id;
        this.accountNo = username;
        this.password = password;
        this.identity = role;
        this.email = email;
    }
}
