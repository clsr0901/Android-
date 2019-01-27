package cn.humiao.myserialport.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserModel implements Serializable {
    private String Address;
    private String Code;
    private String CreateDT;
    private int CreateUserID;
    private String CreateUserName;
    private String Icon;
    private int ID;
    private boolean IsEnable;
    private boolean IsVerify;
    private String LoginAccount;
    private int LoginStrategy;
    private String Name;
    private String OrganizationCaption;
    private int OrganizationID;
    private String Password;
    private String Remark;
    private String RoleCaption;
    private int RoleID;
    private int State;
    private String Telphone;

}
