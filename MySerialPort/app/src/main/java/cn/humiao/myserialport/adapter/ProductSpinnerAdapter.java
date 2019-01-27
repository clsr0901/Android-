package cn.humiao.myserialport.adapter;

import android.content.Context;

import java.util.List;

import cn.humiao.myserialport.entity.ProductModel;
import cn.humiao.myserialport.view.spinner.NiceSpinnerBaseAdapter;

public class ProductSpinnerAdapter extends NiceSpinnerBaseAdapter {
    private final List<ProductModel> mItems;

    public ProductSpinnerAdapter(Context context, List<ProductModel> items, int textColor, int backgroundSelector) {
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
    public ProductModel getItemInDataset(int position) {
        return mItems.get(position);
    }
}
