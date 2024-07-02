package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.AccountMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Account;
import com.kaifa.project.studentenrollmentsysytem.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}