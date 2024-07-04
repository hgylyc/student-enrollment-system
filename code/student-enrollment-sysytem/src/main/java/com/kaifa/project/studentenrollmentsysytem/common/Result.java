package com.kaifa.project.studentenrollmentsysytem.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private String code;
    private String msg;
    private Object data;
   //成功有参数
    public static Result success(String msg,Object data){
        return new Result("200",msg,data);
    }
    //错误有参数
    public static Result error(String msg,Object data){
        return new Result("500",msg, data);
    }
}
