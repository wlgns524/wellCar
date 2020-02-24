package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.ImageAddViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.ImageViewHolder;
import com.rightcode.wellcar.R;

import java.util.ArrayList;

import lombok.Setter;

public class ImageRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener{
    private Context mContext;
    private final int TYPE_ADD = 0;
    private final int TYPE_IMAGE = 1;
    @Setter
    private ArrayList<String> data;

    public ImageRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_add_recyclerview, viewGroup, false);
            return new ImageAddViewHolder(view, mContext);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_recyclerview, viewGroup, false);
            return new ImageViewHolder(view, mContext);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        if (viewHolder instanceof ImageAddViewHolder) {
            ((ImageAddViewHolder) viewHolder).onBind();
        } else if (viewHolder instanceof ImageViewHolder) {
            ((ImageViewHolder) viewHolder).onBind(data.get(i));
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 1;
        } else if (data != null && data.size() == 0) {
            return 1;
        } else {
            return data.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null) {
            return TYPE_ADD;
        } else if (data != null && data.size() == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_IMAGE;
        }
    }

    @Override
    public void onClick(View v) {

    }
}
