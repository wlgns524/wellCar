package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkCompanyViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.TalkCustomerViewHolder;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoom;

import java.util.ArrayList;

import lombok.Setter;

public class TalkListRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    private final int TYPE_CUSTOMER = 1;
    private final int TYPE_COMPANY = 2;

    @Setter
    private ArrayList<ChatRoom> data;

    public TalkListRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_common_recyclerview, viewGroup, false);
        ;
        if (viewType == TYPE_CUSTOMER) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_customer_review_recyclerview, viewGroup, false);
            return new TalkCustomerViewHolder(view, mContext);
        } else if (viewType == TYPE_COMPANY) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_talk_company_review_recyclerview, viewGroup, false);
            return new TalkCompanyViewHolder(view, mContext);
        }
        return new CommonRecyclerViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int i) {
        if (viewHolder instanceof TalkCustomerViewHolder) {
            ((TalkCustomerViewHolder) viewHolder).onBind(data.get(i));
        } else if (viewHolder instanceof TalkCompanyViewHolder) {
            ((TalkCompanyViewHolder) viewHolder).onBind(data.get(i));
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (MemberManager.getInstance(mContext).getUserInfo().getRole() != null) {
            if (MemberManager.getInstance(mContext).getUserInfo().getRole().equals(DataEnums.UserType.CUSTOMER)) {
                return TYPE_CUSTOMER;
            } else if (MemberManager.getInstance(mContext).getUserInfo().getRole().equals(DataEnums.UserType.COMPANY)) {
                return TYPE_COMPANY;
            }
        }
        return 0;
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
