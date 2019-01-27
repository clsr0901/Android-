package cn.humiao.myserialport.util.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenliangsen on 2017/3/28.
 */

public class ToastUtil {
    public static final int ERROR = 0;
    public static final int WARNINＧ = 1;
    public static final int INFO = 2;
    public static final int SUCCES = 3;

    public static void showToast(Context context, String content, int type) {
        switch (type){
            case ERROR:
                Toasty.error(context, content, Toast.LENGTH_SHORT, true).show();
                break;
            case WARNINＧ:
                Toasty.warning(context, content, Toast.LENGTH_SHORT, true).show();
                break;
            case INFO:
                Toasty.info(context, content, Toast.LENGTH_SHORT, true).show();
                break;
            case SUCCES:
                Toasty.success(context, content, Toast.LENGTH_SHORT, true).show();
                break;
            default:
                Toasty.info(context, content, Toast.LENGTH_SHORT, true).show();
                break;
        }
    }
}
