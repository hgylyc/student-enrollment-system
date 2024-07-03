package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kaifa.project.studentenrollmentsysytem.mapper.InstituteMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;
import com.kaifa.project.studentenrollmentsysytem.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstituteServiceImpl extends ServiceImpl<InstituteMapper, Institute> implements InstituteService {
    @Autowired
    private InstituteMapper instituteMapper;
    public Integer getTotalNumOfArrivedStu() {
        return instituteMapper.getTotalNumOfArrivedStu();
    }
}