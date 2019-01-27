package cn.humiao.myserialport.entity;

import lombok.Data;

/**
 * Created by chenliangsen on 2017/9/14.
 */
@Data
public class ResponseModel<T> {
    private int Code;
    private String Msg;
    private T Data;

    public ResponseModel() {
    }

    public ResponseModel(int code, String msg, T data) {
        Code = code;
        Msg = msg;
        Data = data;
    }

}
