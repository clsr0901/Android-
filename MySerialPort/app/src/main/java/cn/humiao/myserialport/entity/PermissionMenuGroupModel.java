package cn.humiao.myserialport.entity;

import lombok.Data;

@Data
public class PermissionMenuGroupModel {
    private String Caption;
    private int ID;
    private boolean IsEnable;
    private int OrderIndex;
    private String Path;
    private String Remark;
}
