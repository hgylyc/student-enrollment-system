package com.kaifa.project.studentenrollmentsysytem.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kaifa.project.studentenrollmentsysytem.mapper.DormitoryMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Dormitory;
import com.kaifa.project.studentenrollmentsysytem.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService{
    @Autowired
    private DormitoryMapper dormitoryMapper;
    public Integer getMaxNumOfbed() {
        return dormitoryMapper.getMaxNumOfbed();
    }
    public Integer getLeftNumOfbed() {
        return dormitoryMapper.getLeftNumOfbed();
    }

    public List<Map<String, Object>> getStudentCountByArea() {
        return dormitoryMapper.getStudentCountByArea();
    }
}
