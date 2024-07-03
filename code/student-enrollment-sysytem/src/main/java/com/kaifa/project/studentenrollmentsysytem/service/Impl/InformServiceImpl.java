package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.InformMapper;
import com.kaifa.project.studentenrollmentsysytem.mapper.InstituteMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Inform;
import com.kaifa.project.studentenrollmentsysytem.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InformServiceImpl extends ServiceImpl<InformMapper, Inform> implements InformService {

}