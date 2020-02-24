package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.ImageViewViewHolder;
import com.rightcode.wellcar.R;

import java.util.ArrayList;

import lombok.Setter;

public class ImageViewRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder>{
    private Context mContext;
    private final int TYPE_ADD = 0;
    private final int TYPE_IMAGE = 1;
    @Setter
    private ArrayList<String> data;

    public ImageViewRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image_view_recyclerview, viewGroup, false);
        return new ImageViewViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        ((ImageViewViewHolder) viewHolder).onBind(data.get(i));
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
