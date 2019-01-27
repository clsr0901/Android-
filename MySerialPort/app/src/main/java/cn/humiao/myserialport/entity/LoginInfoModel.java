package cn.humiao.myserialport.entity;

import lombok.Data;

@Data
public class LoginInfoModel {
    private String ClientVersion;
    private String HostInfo;
    private String HostIP;
    private String LoginAccount;
    private String MeID;
    private String Password;
    private String Type;
    private String VerifyCode;

}
