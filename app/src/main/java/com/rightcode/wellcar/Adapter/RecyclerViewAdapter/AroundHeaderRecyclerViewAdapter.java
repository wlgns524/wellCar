package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.AroundHeaderItemViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.R;

import java.util.ArrayList;

import lombok.Setter;

public class AroundHeaderRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {


    private Context mContext;
    @Setter
    private ArrayList<String> data;
    @Setter
    private ArrayList<Boolean> isSelect;

    public AroundHeaderRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_around_header_item_recyclerview, viewGroup, false);
        return new AroundHeaderItemViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof AroundHeaderItemViewHolder)
            ((AroundHeaderItemViewHolder) viewHolder).onBind(data.get(position), isSelect.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }
}
