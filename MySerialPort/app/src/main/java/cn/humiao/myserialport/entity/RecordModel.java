package cn.humiao.myserialport.entity;

import lombok.Data;

@Data
public class RecordModel {
    private String CreateDT;
    private int CreateUserID;
    private String CreateUserName;
    private String Farea;//原料产地
    private String Fbarcoder;//条码
    private String FBatchnumber;//产品批号
    private float FGrossweight;//毛重
    private int Fid;//编号
    private String Fname;//产品名称
    private String Fnumber;//称编号
    private String Forder;//订单号
    private String Fsign;//牌号
    private int Fstate;//状态
    private float Fsuttle;//净重
    private float FTare;//皮重
    private String Fworker;//工号
    private int ID;

}
