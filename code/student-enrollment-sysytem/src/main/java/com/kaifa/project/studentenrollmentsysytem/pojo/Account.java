package com.kaifa.project.studentenrollmentsysytem.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId
    public int id;

    public String account_no;
    public String password;
    public String identity;
    public String email;
}
