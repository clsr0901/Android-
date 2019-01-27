package cn.humiao.myserialport.adapter;

import android.content.Context;

import java.util.List;

import cn.humiao.myserialport.entity.BatchModel;
import cn.humiao.myserialport.entity.FieldModel;
import cn.humiao.myserialport.view.spinner.NiceSpinnerBaseAdapter;

public class BatchSpinnerAdapter extends NiceSpinnerBaseAdapter {
    private final List<BatchModel> mItems;

    public BatchSpinnerAdapter(Context context, List<BatchModel> items, int textColor, int backgroundSelector) {
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
    public BatchModel getItemInDataset(int position) {
        return mItems.get(position);
    }
}
