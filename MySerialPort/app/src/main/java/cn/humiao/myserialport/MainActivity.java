package cn.humiao.myserialport;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import android_serialport_api.SerialPortFinder;
import cn.humiao.myserialport.Retrofit.HttpBase;
import cn.humiao.myserialport.Retrofit.HttpGetService;
import cn.humiao.myserialport.adapter.BatchSpinnerAdapter;
import cn.humiao.myserialport.adapter.FieldSpinnerAdapter;
import cn.humiao.myserialport.adapter.MarkSpinnerAdapter;
import cn.humiao.myserialport.adapter.ProductSpinnerAdapter;
import cn.humiao.myserialport.dialog.InputPasswordPopWin;
import cn.humiao.myserialport.dialog.PublicDialog;
import cn.humiao.myserialport.entity.BatchModel;
import cn.humiao.myserialport.entity.FieldModel;
import cn.humiao.myserialport.entity.MarkModel;
import cn.humiao.myserialport.entity.PageQueryModel;
import cn.humiao.myserialport.entity.ProductModel;
import cn.humiao.myserialport.entity.RecordModel;
import cn.humiao.myserialport.entity.ResponseModel;
import cn.humiao.myserialport.entity.UserModel;
import cn.humiao.myserialport.login.LoginActivity;
import cn.humiao.myserialport.util.MD5Util;
import cn.humiao.myserialport.util.StringUtils;
import cn.humiao.myserialport.util.toast.ToastUtil;
import cn.humiao.myserialport.view.spinner.NiceSpinner;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private UserModel userModel;
    private SerialPortUtil serialPortUtil;
    private NiceSpinner product_name, mark, field, batch_spinner1;
    private TextView login_acount, user_name, organization, create_dt, tel, addr, gross, tare, net_weight;
    private EditText bar_code;
    private ImageView user_info, icon;
    private DrawerLayout activity_home;
    private LinearLayout right;
    private RelativeLayout left;
    private PublicDialog dialog;
    private InputPasswordPopWin inputPasswordPopWin;
    private HttpGetService service;

    private List<ProductModel> productModels;
    private ProductModel selectProduct;

    private List<MarkModel> markModels;
    private MarkModel selectMark;

    private List<FieldModel> fieldModels;
    private FieldModel selectField;

    private List<BatchModel> batchModels;
    private BatchModel selectBatch;
    private String weight = "0000";
    float tareWeight = 0.0f;
    float grossWeight = 0.0f;
    float netWeight = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //这个特性是andorid4.4支持的，最少要api19才可以使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getSupportActionBar().hide();
            } catch (Exception e) {
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serialPortUtil = new SerialPortUtil();
        serialPortUtil.openSerialPort(MainActivity.this);
        //注册EventBus
        EventBus.getDefault().register(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        activity_home = (DrawerLayout) findViewById(R.id.activity_home);
        right = (LinearLayout) findViewById(R.id.right);
        left = (RelativeLayout) findViewById(R.id.left);
        findViewById(R.id.tare_button).setOnClickListener(this);
        findViewById(R.id.gross_button).setOnClickListener(this);
        findViewById(R.id.submit).setOnClickListener(this);
        findViewById(R.id.rl_exit).setOnClickListener(this);
        findViewById(R.id.exit).setOnClickListener(this);
        findViewById(R.id.change_password_rl).setOnClickListener(this);
        findViewById(R.id.change_password).setOnClickListener(this);
        user_info = (ImageView) findViewById(R.id.user_info);
        icon = (ImageView) findViewById(R.id.icon);
        user_info.setOnClickListener(this);
        product_name = (NiceSpinner) findViewById(R.id.product_name);
        mark = (NiceSpinner) findViewById(R.id.mark);
        field = (NiceSpinner) findViewById(R.id.field);
        batch_spinner1 = (NiceSpinner) findViewById(R.id.batch_spinner1);
        login_acount = (TextView) findViewById(R.id.login_acount);
        user_name = (TextView) findViewById(R.id.user_name);
        organization = (TextView) findViewById(R.id.organization);
        create_dt = (TextView) findViewById(R.id.create_dt);
        tel = (TextView) findViewById(R.id.tel);
        addr = (TextView) findViewById(R.id.addr);
        tare = (TextView) findViewById(R.id.tare);
        gross = (TextView) findViewById(R.id.gross);
        net_weight = (TextView) findViewById(R.id.net_weight);
        bar_code = (EditText) findViewById(R.id.bar_code);
        RequestOptions options = new RequestOptions()
                .centerCrop().error(getResources().getDrawable(R.drawable.user_info))
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(MainActivity.this)
                .load(userModel.getIcon())
                .apply(options)
                .into(user_info);
        Glide.with(MainActivity.this)
                .load(userModel.getIcon())
                .apply(options)
                .into(icon);
        login_acount.setText(userModel.getLoginAccount());
        user_name.setText(userModel.getName());
        organization.setText(userModel.getOrganizationCaption());
        create_dt.setText(userModel.getCreateDT());
        tel.setText(userModel.getTelphone());
        addr.setText(userModel.getAddress());
    }

    private void initData() {
        Retrofit retrofit = HttpBase.getRetrofit();
        service = retrofit.create(HttpGetService.class);
        getProduct();
        getMark();
        getField();
        getBatch();
    }


    /**
     * 用EventBus进行线程间通信，也可以使用Handler
     *
     * @param string
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(String string) {
        Log.e("fuck", "Subscribe" + string);
        Log.e("fuck", "Subscribe" + string.substring(6, 10));
//        Log.d(TAG,"获取到了从传感器发送到Android主板的串口数据");
        weight = string.substring(6, 10);
    }

    private void getProduct() {
        service.product(new PageQueryModel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel<List<ProductModel>>>() {
                    @Override
                    public void onNext(ResponseModel<List<ProductModel>> responseModel) {
                        if (responseModel.getCode() == 200) {
                            productModels = responseModel.getData();
                            selectProduct = productModels.get(0);
                            product_name.setTextColor(getResources().getColor(R.color.color_green));
                            ProductSpinnerAdapter productSpinnerAdapter = new ProductSpinnerAdapter(MainActivity.this,
                                    productModels, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                            product_name.setAdapter(productSpinnerAdapter);
                            product_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectProduct = productModels.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }

    private void getMark() {
        service.mark(new PageQueryModel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel<List<MarkModel>>>() {
                    @Override
                    public void onNext(ResponseModel<List<MarkModel>> responseModel) {
                        if (responseModel.getCode() == 200) {
                            markModels = responseModel.getData();
                            selectMark = markModels.get(0);
                            mark.setTextColor(getResources().getColor(R.color.color_green));
                            MarkSpinnerAdapter markSpinnerAdapter = new MarkSpinnerAdapter(MainActivity.this,
                                    markModels, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                            mark.setAdapter(markSpinnerAdapter);
                            mark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectMark = markModels.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }

    private void getField() {
        service.field(new PageQueryModel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel<List<FieldModel>>>() {
                    @Override
                    public void onNext(ResponseModel<List<FieldModel>> responseModel) {
                        if (responseModel.getCode() == 200) {
                            fieldModels = responseModel.getData();
                            selectField = fieldModels.get(0);
                            field.setTextColor(getResources().getColor(R.color.color_green));
                            FieldSpinnerAdapter fieldSpinnerAdapter = new FieldSpinnerAdapter(MainActivity.this,
                                    fieldModels, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                            field.setAdapter(fieldSpinnerAdapter);
                            field.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectField = fieldModels.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }

    private void getBatch() {
        service.batch(new PageQueryModel())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel<List<BatchModel>>>() {
                    @Override
                    public void onNext(ResponseModel<List<BatchModel>> responseModel) {
                        if (responseModel.getCode() == 200) {
                            batchModels = responseModel.getData();
                            selectBatch = batchModels.get(0);
                            batch_spinner1.setTextColor(getResources().getColor(R.color.color_green));
                            BatchSpinnerAdapter batchSpinnerAdapter = new BatchSpinnerAdapter(MainActivity.this,
                                    batchModels, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
                            batch_spinner1.setAdapter(batchSpinnerAdapter);
                            batch_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    selectBatch = batchModels.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tare_button:
                //TODO 皮重
                tareWeight = getWeight();
                tare.setText(tareWeight + "");
                netWeight = grossWeight - tareWeight;
                net_weight.setText(netWeight + "");
                break;
            case R.id.gross_button:
                //TODO 毛重
                grossWeight = getWeight();
                gross.setText(grossWeight+ "");
                netWeight = grossWeight - tareWeight;
                net_weight.setText(netWeight + "");
                break;
            case R.id.submit:
                submit();
                break;
            case R.id.user_info:
                if (activity_home.isDrawerOpen(left)) {
                    activity_home.closeDrawer(left);
                } else {
                    activity_home.openDrawer(left);
                }
                break;
            case R.id.rl_exit:
                showLogoutDialog();
                break;
            case R.id.exit:
                showLogoutDialog();
                break;
            case R.id.change_password_rl:
                showPasswordDialog();
                break;
            case R.id.change_password:
                showPasswordDialog();
                break;
        }
    }

    private float getWeight(){
        String[] numbers = weight.split("");
        if (numbers[1].equals("")){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(numbers[2]);
            stringBuffer.append(".");
            stringBuffer.append(numbers[3]);
            stringBuffer.append(numbers[4]);
            return Float.parseFloat(stringBuffer.toString());
        }else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(numbers[1]);
            stringBuffer.append(numbers[2]);
            stringBuffer.append(".");
            stringBuffer.append(numbers[3]);
            stringBuffer.append(numbers[4]);
            return Float.parseFloat(stringBuffer.toString());
        }
    }
    private void submit() {
        RecordModel recordModel = new RecordModel();
        if (selectField != null)
            recordModel.setFarea(selectField.getCaption());
        if (StringUtils.isNull(bar_code.getText().toString())) {
            ToastUtil.showToast(MainActivity.this, "请先录入条码", ToastUtil.WARNINＧ);
            return;
        }
        recordModel.setFbarcoder(bar_code.getText().toString());
        if (selectBatch != null)
            recordModel.setFBatchnumber(selectBatch.getCaption());
        recordModel.setFGrossweight(grossWeight);//毛重
        recordModel.setFsuttle(netWeight);//净重
        recordModel.setFTare(tareWeight);//皮重
        if (selectProduct != null)
            recordModel.setFname(selectProduct.getCaption());
        if (selectMark != null)
            recordModel.setFsign(selectMark.getCaption());
        service.save(recordModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel.getCode() == 200) {
                            ToastUtil.showToast(MainActivity.this, "保存成功", ToastUtil.SUCCES);
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showLogoutDialog();
        }
        return false;
    }

    private void showLogoutDialog() {
        dialog = new PublicDialog(MainActivity.this);
        dialog.setDialogView("确定退出？", -1);
        dialog.setButtonBlue("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setButtonRed("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        dialog.showAtLocation(findViewById(R.id.activity_home), Gravity.CENTER, 0, 0);
    }

    private void logout() {
        service.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onNext(ResponseModel responseModel) {

                    }

                    @Override
                    public void onCompleted() {
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private void showPasswordDialog() {
        inputPasswordPopWin = new InputPasswordPopWin(MainActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String oldPassword = inputPasswordPopWin.text_mobile.getText().toString().trim();
                        String newPassword = inputPasswordPopWin.newPassword.getText().toString().trim();
                        if (StringUtils.isNull(oldPassword)) {
                            ToastUtil.showToast(MainActivity.this, "请输入旧密码", ToastUtil.WARNINＧ);
                            return;
                        }
                        if (StringUtils.isNull(newPassword)) {
                            ToastUtil.showToast(MainActivity.this, "请输入新密码", ToastUtil.WARNINＧ);
                            return;
                        }
                        changePassword(MD5Util.enMd(oldPassword), MD5Util.enMd(newPassword));
                    }
                });
        inputPasswordPopWin.showAtLocation(findViewById(R.id.activity_home), Gravity.CENTER, 0, 0);
    }

    private void changePassword(String oldPassword, String newPassword) {
        service.changePassword(oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseModel>() {
                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel.getCode() == 200) {
                            inputPasswordPopWin.dismiss();
                            ToastUtil.showToast(MainActivity.this, "请使用新密码登陆", ToastUtil.SUCCES);
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            ToastUtil.showToast(MainActivity.this, responseModel.getMsg(), ToastUtil.ERROR);
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast(MainActivity.this, e.getMessage(), ToastUtil.ERROR);
                    }
                });
    }
}
