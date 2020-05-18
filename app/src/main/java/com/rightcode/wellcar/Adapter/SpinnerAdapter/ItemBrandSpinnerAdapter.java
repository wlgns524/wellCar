package com.rightcode.wellcar.Adapter.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;

import lombok.Setter;

public class ItemBrandSpinnerAdapter extends BaseAdapter {

    @Setter
    ItemBrand[] data;
    Context context;
    LayoutInflater inflater;
//    @Setter
//    @Getter
//    private int position = 0;

    public ItemBrandSpinnerAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if (data != null) return data.length;
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_spinner, parent, false);
        }

        if (data != null) {
            //데이터세팅
            String text = data[position].getName();
            ((TextView) convertView.findViewById(R.id.tv_spinner)).setText(text);
            Glide.with(context)
                    .load(data[position].getImageName())
                    .into(((ImageView) convertView.findViewById(R.id.iv_brand_logo)));
            ;
        }

        return convertView;
    }

    @Override
    public ItemBrand getItem(int position) {
        try {
            return data[position];
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

