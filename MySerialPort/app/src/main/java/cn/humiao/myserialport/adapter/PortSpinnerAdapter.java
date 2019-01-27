package cn.humiao.myserialport.adapter;

import android.content.Context;

import java.util.List;

import cn.humiao.myserialport.view.spinner.NiceSpinnerBaseAdapter;

public class PortSpinnerAdapter extends NiceSpinnerBaseAdapter {
    private final List<String> mItems;

    public PortSpinnerAdapter(Context context, List<String> items, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public String getItemInDataset(int position) {
        return mItems.get(position);
    }
}
