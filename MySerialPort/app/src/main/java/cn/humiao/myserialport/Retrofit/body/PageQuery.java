package cn.humiao.myserialport.Retrofit.body;

import java.util.HashMap;

import lombok.Data;

/**
 * Created by chenliangsen on 2017/8/8.
 */
@Data
public class PageQuery {
    private HashMap<String, String> Conditions;
    private int PageIndex;
    private int PageSize;
    private double Lng;
    private double Lat;
    private int Zoom;

}
