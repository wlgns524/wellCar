package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkDetailMineReviewViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkDetailYoursReviewViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkDetailYoursViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkDetailMineViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoomDetail;

import java.util.ArrayList;

import lombok.Setter;

public class TalkDetailRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    private final int TYPE_MINE = 1;
    private final int TYPE_YOURS = 2;
    private final int TYPE_REVIEW_MINE = 3;
    private final int TYPE_REVIEW_YOURS = 4;

    @Setter
    private Integer companyId;
    @Setter
    private ArrayList<ChatRoomDetail> data;

    public TalkDetailRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_MINE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_detail_mine_review_recyclerview, viewGroup, false);
            return new TalkDetailMineViewHolder(view, mContext);
        } else if (viewType == TYPE_YOURS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_detail_yours_review_recyclerview, viewGroup, false);
            return new TalkDetailYoursViewHolder(view, mContext);
        } else if (viewType == TYPE_REVIEW_MINE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_detail_mine_review_review_recyclerview, viewGroup, false);
            return new TalkDetailMineReviewViewHolder(view, mContext);
        } else if (viewType == TYPE_REVIEW_YOURS) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_detail_yours_review_review_recyclerview, viewGroup, false);
            return new TalkDetailYoursReviewViewHolder(view, mContext, companyId);
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_common_recyclerview, viewGroup, false);
            return new CommonRecyclerViewHolder(view, mContext);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        if (viewHolder instanceof TalkDetailMineViewHolder) {
            ((TalkDetailMineViewHolder) viewHolder).onBind(data.get(i));
        } else if (viewHolder instanceof TalkDetailYoursViewHolder) {
            ((TalkDetailYoursViewHolder) viewHolder).onBind(data.get(i));
        } else if (viewHolder instanceof TalkDetailMineReviewViewHolder) {
            ((TalkDetailMineReviewViewHolder) viewHolder).onBind(data.get(i));
        } else if (viewHolder instanceof TalkDetailYoursReviewViewHolder) {
            ((TalkDetailYoursReviewViewHolder) viewHolder).onBind(data.get(i));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (data.get(position).getIsMine()) {
            if (data.get(position).getDiff().equals(DataEnums.TalkDiffType.REVIEW))
                return TYPE_REVIEW_MINE;
            return TYPE_MINE;
        } else {
            if (data.get(position).getDiff().equals(DataEnums.TalkDiffType.REVIEW))
                return TYPE_REVIEW_YOURS;
            return TYPE_YOURS;
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    public void addItem(ChatRoomDetail data) {
        this.data.add(data);
        notifyItemChanged(this.data.size() - 1);
    }
}
