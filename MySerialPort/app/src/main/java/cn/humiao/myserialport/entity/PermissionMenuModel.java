package cn.humiao.myserialport.entity;

import lombok.Data;

@Data
public class PermissionMenuModel {
    private String Caption;
    private int ID;
    private boolean IsEnable;
    private int OrderIndex;
    private String Path;
    private String PermissionMenuGroupCaption;
    private int PermissionMenuGroupID;
    private String Remark;
}
