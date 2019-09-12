package xyz.grinner.library.dataobj.model;

import lombok.Data;

/**
 * @Author: chenkai
 * @Date: 2019/9/12 10:00
 */
@Data
public class Result {
    private boolean success;
    private String msg;
    private Object data;

    private Result(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }



    //创建类操作，返回ID等
    public static Result created(Object input){
        String msg = String.valueOf(input);
        return new Result(true,msg,null);
    }

    //更新类操作
    public static Result success(){
        return new Result(true,"操作成功",null);
    }

    //查询类操作，返回数据
    public static Result found(Object data){
        return new Result(true,"",data);
    }

    public static Result fail(String msg){
        return new Result(false,msg,null);
    }
}
