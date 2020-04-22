package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.AroundHeaderViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.AroundViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.BuyTicketHeaderViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.BuyTicketViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.event.Event;
import com.rightcode.wellcar.network.model.response.notice.Notice;
import com.rightcode.wellcar.network.model.response.store.Store;

import java.util.ArrayList;

import lombok.Setter;

public class AroundRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    private final Integer TYPE_HEAD = 0;
    private final Integer TYPE_ITEM = 1;

    @Setter
    private ArrayList<Store> data;
    private ArrayList<Event> eventData;
    private AroundHeaderViewHolder aroundHeaderViewHolder;
    @Setter
    private FragmentManager fm;

    public AroundRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_HEAD) {
            if (aroundHeaderViewHolder == null) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_around_header_recyclerview, viewGroup, false);
                aroundHeaderViewHolder = new AroundHeaderViewHolder(view, mContext);
            }
            return aroundHeaderViewHolder;
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_around_recyclerview, viewGroup, false);
            return new AroundViewHolder(view, mContext);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        if (viewHolder instanceof AroundHeaderViewHolder) {
            ((AroundHeaderViewHolder) viewHolder).onBind(fm);
        } else if (viewHolder instanceof AroundViewHolder)
            ((AroundViewHolder) viewHolder).onBind(data.get(position - 1));
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            return 1;
        } else if (data != null && data.size() == 0) {
            return 1;
        } else {
            return data.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }

    public void setEventData(ArrayList<Event> eventData) {
        this.eventData = eventData;
        aroundHeaderViewHolder.setEventData(eventData);
    }
}
