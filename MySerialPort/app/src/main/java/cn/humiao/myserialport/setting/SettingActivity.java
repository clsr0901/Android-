package cn.humiao.myserialport.setting;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import android_serialport_api.SerialPortFinder;
import cn.humiao.myserialport.DataUtils;
import cn.humiao.myserialport.R;
import cn.humiao.myserialport.Retrofit.HttpBase;
import cn.humiao.myserialport.adapter.PortSpinnerAdapter;
import cn.humiao.myserialport.util.InfoUtil;
import cn.humiao.myserialport.util.SharedPreferencesUtil;
import cn.humiao.myserialport.util.StringUtils;
import cn.humiao.myserialport.util.toast.ToastUtil;
import cn.humiao.myserialport.view.spinner.NiceSpinner;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_url;
    private NiceSpinner port_spinner;
    private TextView mac_tv;

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
        setContentView(R.layout.activity_setting);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);
        mac_tv = (TextView) findViewById(R.id.mac_tv);
        et_url = (EditText) findViewById(R.id.et_url);
        port_spinner = (NiceSpinner) findViewById(R.id.port_spinner);
        String url = SharedPreferencesUtil.getStringVaule(SettingActivity.this, "url", HttpBase.getURL());
        et_url.setText(url);

        port_spinner.setTextColor(getResources().getColor(R.color.color_green));
        SerialPortFinder finder = new SerialPortFinder();
        final List<String> ports = Arrays.asList(finder.getAllDevicesPath());
        PortSpinnerAdapter productSpinnerAdapter = new PortSpinnerAdapter(SettingActivity.this,
                ports, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimary));
        port_spinner.setAdapter(productSpinnerAdapter);
        port_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferencesUtil.setStringVaule(SettingActivity.this, "port", ports.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mac_tv.setText(InfoUtil.getIMEI(SettingActivity.this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                if (StringUtils.isNull(et_url.getText().toString())) {
                    ToastUtil.showToast(SettingActivity.this, "请输入服务器URL", ToastUtil.WARNINＧ);
                    return;
                }
                if (!et_url.getText().toString().startsWith("http://") && !et_url.getText().toString().startsWith("https://")) {
                    ToastUtil.showToast(SettingActivity.this, "请输入正确的服务器地址", ToastUtil.WARNINＧ);
                    return;
                }
                SharedPreferencesUtil.setStringVaule(SettingActivity.this, "url", et_url.getText().toString());
                HttpBase.setURL(et_url.getText().toString());
                ToastUtil.showToast(SettingActivity.this, "设置成功", ToastUtil.SUCCES);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
