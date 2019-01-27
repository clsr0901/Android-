package cn.humiao.myserialport.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserLoginInfoModel {
    private String ClientVersion;
    private String HostInfo;
    private String HostIP;
    private boolean IsDefaultPassword;
    private String LoginDT;
    private int LoginLogID;
    private String MeID;
    private OrganizationModel Organization;
    private List<String> PermissionActionArray;
    private List<PermissionMenuModel> PermissionMenuArray;
    private List<PermissionMenuGroupModel> PermissionMenuGroupArray;
    private List<PermissionOperationModel> PermissionOperationArray;
    private List<String> PermissionOperationPathArray;
    private RoleModel Role;
    private String Token;
    private String Type;
    private UserModel User;
    private int UserID;
}
