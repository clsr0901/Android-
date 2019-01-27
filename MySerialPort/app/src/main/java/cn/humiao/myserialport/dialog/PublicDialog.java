package cn.humiao.myserialport.dialog;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.humiao.myserialport.R;


/**
 * @Function: 自定义对话框
 */
public class PublicDialog extends PopupWindow {
    private Button cancel, sure;
    private TextView title;
    private View mView;
    private ImageView id_iv_icon;
    private Context context;
    private EditText input;
    private PublicDialog dialog;

    /**
     * 自定义对话框 必须设置setDialogView()方法
     *
     * @param context
     */
    public PublicDialog(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.public_dialog, null);
        this.dialog = this;


        // 设置SelectPicPopupWindow的View
        this.setContentView(mView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.popup_window_style);
        title = (TextView) mView.findViewById(R.id.id_tv_dialog);
        cancel = (Button) mView.findViewById(R.id.id_btn_cancel);
        sure = (Button) mView.findViewById(R.id.id_btn_sure);
        id_iv_icon = (ImageView) mView.findViewById(R.id.id_iv_icon);
        input = (EditText) mView.findViewById(R.id.input);
    }

    /**
     * 设置显示方式
     *
     * @param _Title 提示框显示的文本
     * @param isNum  全部按钮-1 只显示文字0 蓝色按钮1 红色按钮2 成功图标3 失败图标4 loading图标5
     */
    public void setDialogView(String _Title, int isNum) {
        switch (isNum) {
            case 0:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.GONE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
                id_iv_icon.setVisibility(View.GONE);
                title.setTextSize(18);
                handler.postDelayed(runnable, 1500);
                break;
            case 1:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                sure.setVisibility(View.VISIBLE);
                id_iv_icon.setVisibility(View.GONE);
                break;
            case 2:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                sure.setVisibility(View.GONE);
                id_iv_icon.setVisibility(View.GONE);
                break;
            case 3:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.GONE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
                id_iv_icon.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, 1500);
                break;
            case 4:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.GONE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
                id_iv_icon.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, 1500);
                break;
            case 5:
                mView.findViewById(R.id.id_line).setVisibility(View.GONE);
                mView.findViewById(R.id.id_lines).setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                sure.setVisibility(View.GONE);
                id_iv_icon.setVisibility(View.GONE);
                mView.findViewById(R.id.id_loading).setVisibility(View.VISIBLE);
                break;
            default:
                mView.findViewById(R.id.id_line).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.id_lines).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.id_loading).setVisibility(View.GONE);
                cancel.setVisibility(View.VISIBLE);
                sure.setVisibility(View.VISIBLE);
                id_iv_icon.setVisibility(View.GONE);
                break;
        }
        title.setText(_Title);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            dismiss();
        }
    };

    /**
     * 蓝色按钮监听
     *
     * @param _Sure 按钮1要显示的文本
     * @param click 按钮1点击事件
     */
    public void setButtonBlue(String _Sure, View.OnClickListener click) {
        sure.setText(_Sure);
        sure.setOnClickListener(click);
    }

    /**
     * 设置评价弹框的字体颜色
     */
    public void setColor() {
//		sure.setTextColor(context.getResources().getColor(R.color.color_666666));
//		cancel.setTextColor(context.getResources().getColor(R.color.color_1474da));
    }

    /**
     * 红色按钮监听
     *
     * @param _Cancel 按钮2要显示的文本
     * @param click   按钮2点击事件
     */
    public void setButtonRed(String _Cancel, View.OnClickListener click) {
        cancel.setText(_Cancel);
        cancel.setOnClickListener(click);
    }

    public void setInput(String inputContent) {
        input.setText(inputContent);
        input.setVisibility(View.VISIBLE);
        input.setFocusable(true);
    }

    public String getInput() {
        return input.getText().toString();
    }


}