package cn.humiao.myserialport.adapter;

import android.content.Context;

import java.util.List;

import cn.humiao.myserialport.entity.FieldModel;
import cn.humiao.myserialport.view.spinner.NiceSpinnerBaseAdapter;

public class FieldSpinnerAdapter extends NiceSpinnerBaseAdapter {
    private final List<FieldModel> mItems;

    public FieldSpinnerAdapter(Context context, List<FieldModel> items, int textColor, int backgroundSelector) {
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
    public FieldModel getItemInDataset(int position) {
        return mItems.get(position);
    }
}
