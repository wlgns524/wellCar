package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.NoticeViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.notice.Notice;

import java.util.ArrayList;

import lombok.Setter;

public class NoticeRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Setter
    private ArrayList<Notice> data;

    public NoticeRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice_recyclerview, viewGroup, false);
        return new NoticeViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        ((NoticeViewHolder) viewHolder).onBind(data.get(position));
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
