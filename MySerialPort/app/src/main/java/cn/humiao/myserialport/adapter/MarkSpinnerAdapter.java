package cn.humiao.myserialport.adapter;

import android.content.Context;

import java.util.List;

import cn.humiao.myserialport.entity.MarkModel;
import cn.humiao.myserialport.view.spinner.NiceSpinnerBaseAdapter;

public class MarkSpinnerAdapter extends NiceSpinnerBaseAdapter {
    private final List<MarkModel> mItems;

    public MarkSpinnerAdapter(Context context, List<MarkModel> items, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position).getCaption();
    }

    @Override
    public MarkModel getItemInDataset(int position) {
        return mItems.get(position);
    }
}
