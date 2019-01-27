package cn.humiao.myserialport.entity;

import java.util.HashMap;

import lombok.Data;

@Data
public class PageQueryModel {
    private HashMap<String, String> Conditions;
    private int PageIndex = 1;
    private int PageSize = 100000;

}
