package cn.humiao.myserialport.Retrofit;


import java.util.List;

import cn.humiao.myserialport.entity.BatchModel;
import cn.humiao.myserialport.entity.FieldModel;
import cn.humiao.myserialport.entity.LoginInfoModel;
import cn.humiao.myserialport.entity.MarkModel;
import cn.humiao.myserialport.entity.PageQueryModel;
import cn.humiao.myserialport.entity.ProductModel;
import cn.humiao.myserialport.entity.RecordModel;
import cn.humiao.myserialport.entity.ResponseModel;
import cn.humiao.myserialport.entity.UserLoginInfoModel;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by chenliangsen on 2017/6/26.
 */

public interface HttpGetService {

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Login/Login")
    Observable<ResponseModel<UserLoginInfoModel>> login(@Body LoginInfoModel loginInfoModel);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Login/Logout")
    Observable<ResponseModel> logout();

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Public/QueryProduct")
    Observable<ResponseModel<List<ProductModel>>> product(@Body PageQueryModel pageQueryModel);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Public/QueryMark")
    Observable<ResponseModel<List<MarkModel>>> mark(@Body PageQueryModel pageQueryModel);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Public/QueryField")
    Observable<ResponseModel<List<FieldModel>>> field(@Body PageQueryModel pageQueryModel);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Public/QueryBatch")
    Observable<ResponseModel<List<BatchModel>>> batch(@Body PageQueryModel pageQueryModel);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Personal/ChangeSelfPassword")
    @FormUrlEncoded
    Observable<ResponseModel<String>> changePassword(@Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @Headers({
            "contentType: application/json",
            "dataType: json"
    })
    @POST("/Record/Save")
    Observable<ResponseModel> save(@Body RecordModel recordModel);
}