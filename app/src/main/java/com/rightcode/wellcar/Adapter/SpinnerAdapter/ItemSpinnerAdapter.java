package com.rightcode.wellcar.Adapter.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;

import lombok.Getter;
import lombok.Setter;

public class ItemSpinnerAdapter extends BaseAdapter {

    @Setter
    Item[] data;
    Context context;
    LayoutInflater inflater;
    @Setter
    @Getter
    private int position = 0;

    public ItemSpinnerAdapter(Context context) {
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
        }

        return convertView;
    }

    @Override
    public Item getItem(int position) {
        try {
            return data[position];
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

