package com.rightcode.wellcar.Adapter.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rightcode.wellcar.R;

import lombok.Setter;

public class SpinnerAdapter extends BaseAdapter {

    @Setter
    String[] data;
    Context context;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context) {
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
            String text = data[position];
            ((TextView) convertView.findViewById(R.id.tv_spinner)).setText(text);
        }

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

