package cn.humiao.myserialport.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.humiao.myserialport.MainActivity;
import cn.humiao.myserialport.R;
import cn.humiao.myserialport.Retrofit.HttpBase;
import cn.humiao.myserialport.Retrofit.HttpGetService;
import cn.humiao.myserialport.entity.LoginInfoModel;
import cn.humiao.myserialport.entity.ResponseModel;
import cn.humiao.myserialport.entity.UserLoginInfoModel;
import cn.humiao.myserialport.setting.SettingActivity;
import cn.humiao.myserialport.util.InfoUtil;
import cn.humiao.myserialport.util.MD5Util;
import cn.humiao.myserialport.util.SharedPreferencesUtil;
import cn.humiao.myserialport.util.StringUtils;
import cn.humiao.myserialport.util.toast.ToastUtil;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText et_username, et_password;
    private Button bt_login;
    private ImageView iv_hintpassword;
    private boolean showPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getSupportActionBar().hide();
            } catch (Exception e) {
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        HttpBase.setURL(SharedPreferencesUtil.getStringVaule(LoginActivity.this, "url", HttpBase.getURL()));
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_login = (Button) findViewById(R.id.bt_login);
        iv_hintpassword = (ImageView) findViewById(R.id.iv_hintpassword);
        iv_hintpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassword = !showPassword;
                if (showPassword) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_hintpassword.setImageResource(R.drawable.icon_visible);
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    iv_hintpassword.setImageResource(R.drawable.icon_unvisible);
                }
            }
        });
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNull(et_username.getText().toString())) {
                    ToastUtil.showToast(LoginActivity.this, "请输入用户名", ToastUtil.WARNINＧ);
                    return;
                }
                if (StringUtils.isNull(et_password.getText().toString())) {
                    ToastUtil.showToast(LoginActivity.this, "请输入密码", ToastUtil.WARNINＧ);
                    return;
                }
                login();
            }
        });
        findViewById(R.id.login_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SettingActivity.class));
            }
        });
    }

    private void login() {
        Retrofit retrofit = HttpBase.getRetrofit();
        final HttpGetService getService = retrofit.create(HttpGetService.class);
        LoginInfoModel loginInfoModel = new LoginInfoModel();
        loginInfoModel.setLoginAccount(et_username.getText().toString());
        loginInfoModel.setPassword(MD5Util.enMd(et_password.getText().toString()));
        loginInfoModel.setMeID(InfoUtil.getIMEI(LoginActivity.this));
        loginInfoModel.setType("Android");
        loginInfoModel.setClientVersion("1.0");
        loginInfoModel.setHostInfo("");
//        loginInfoModel.setHostInfo(InfoUtil.getHostNameInfo(LoginActivity.this));
        getService.login(loginInfoModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel<UserLoginInfoModel>>() {
                    @Override
                    public void onNext(ResponseModel<UserLoginInfoModel> responseModel) {
                        if (responseModel.getCode() == 200) {
                            HttpBase.setTOKEN(responseModel.getData().getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", responseModel.getData().getUser());
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtil.showToast(LoginActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(LoginActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }
}
