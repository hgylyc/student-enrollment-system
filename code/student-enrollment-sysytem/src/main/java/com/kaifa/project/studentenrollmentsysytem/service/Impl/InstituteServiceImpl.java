package com.kaifa.project.studentenrollmentsysytem.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.kaifa.project.studentenrollmentsysytem.mapper.InstituteMapper;
import com.kaifa.project.studentenrollmentsysytem.pojo.Institute;
import com.kaifa.project.studentenrollmentsysytem.pojo.Mapping;
import com.kaifa.project.studentenrollmentsysytem.service.InstituteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstituteServiceImpl extends ServiceImpl<InstituteMapper, Institute> implements InstituteService {
    @Autowired
    private InstituteMapper instituteMapper;
    public List<Map<String, Object>>  getTotalNumOfArrivedStu() {
        return instituteMapper.getTotalNumOfArrivedStu();
    }

    public List<Map<String, Object>> getStudentByInstitute() {
        List<Map<String, Object>>institutes=instituteMapper.getStudentByInstitute();
        institutes.forEach(institute -> {

            String instituteNameStr = (String)institute.get("institute_name");
            char a = instituteNameStr.charAt(0);
            institute.put("Ins_name", Mapping.reverseMapCollege(a));
          // institute.remove("institute_name");
        }
        );
        return institutes;



    };
    public Institute getInstituteByName(String instituteName) {
        return instituteMapper.selectByName(instituteName);
    }

    public boolean updateInstituteInfo(Institute institute) {
        return instituteMapper.updateById(institute) > 0;
    }
    //返回报道率最低的三个学院
    public List<Map<String, Object>> getInstitutesWithLowestArrivalRate() {
        List<Map<String, Object>> institutes = getStudentByInstitute();

        // 计算报道率并添加到每个学院的数据中
        //将学院改成中文
        institutes.forEach(institute -> {
            int numOfStudents = (int) institute.get("num_of_student");
            int numOfArrivedStudents = (int) institute.get("num_of_arrived_stu");
            double arrivalRate = (double) numOfArrivedStudents / numOfStudents;
            institute.put("arrival_rate", arrivalRate);

            String instituteNameStr = (String)institute.get("institute_name");
            char a = instituteNameStr.charAt(0);
            institute.put("Ins_name", Mapping.reverseMapCollege(a));
            //institute.remove("institute_name");
        });

        // 按报道率排序并取出前三个报道率最低的学院
        return institutes.stream()
                .sorted(Comparator.comparingDouble(institute -> (double) institute.get("arrival_rate")))
                .limit(3)
                .collect(Collectors.toList());
    }
}
