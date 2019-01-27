package cn.humiao.myserialport.entity;

import lombok.Data;

@Data
public class PermissionOperationModel {
    private String Caption;
    private int ID;
    private boolean IsMenuBasicNeed;
    private String Path;
    private String PermissionMenuCaption;
    private int PermissionMenuID;
    private String Remark;
}
