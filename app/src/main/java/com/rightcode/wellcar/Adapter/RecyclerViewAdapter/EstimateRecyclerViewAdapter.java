package com.rightcode.wellcar.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.ViewHolder.CommonRecyclerViewHolder;
import com.rightcode.wellcar.Adapter.ViewHolder.EstimateyViewHolder;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.SelectEvent;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.store.Store;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class EstimateRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;

    @Event(SelectEvent.class)
    public void onSelectEvent(SelectEvent event) {
//        Log.d(event.getIsSelect());
//        Log.d(event.getPosition());
        int selectCount = 0;
        if (mSelectList != null) {
            ArrayList<Boolean> list = mSelectList;
            list.set(event.getPosition(), event.getIsSelect());
            for (Boolean flag : list) {
                if (flag)
                    selectCount++;
            }
            if (selectCount > 5) {
                ToastUtil.show(mContext, "최대 5개까지 선택가능합니다");
                return;
            }
            initSelectList();
            mSelectList.set(event.getPosition(), event.getIsSelect());
            notifyItemChanged(event.getPosition());
        } else {
            initSelectList();
        }
    }

    @Getter
    @Setter
    private ArrayList<Store> data;
    public ArrayList<Boolean> mSelectList;


    public EstimateRecyclerViewAdapter(Context context) {
        this.mContext = context;
        RxBus.register(this);
    }

    @NonNull
    @Override
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_company_recyclerview, viewGroup, false);
        initSelectList();
        return new EstimateyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder viewHolder, int position) {
        Log.d(mSelectList.get(position));
        ((EstimateyViewHolder) viewHolder).onBind(data.get(position), position, mSelectList.get(position));
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        RxBus.unregister(this);
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size();
        }
    }

    private void initSelectList() {
        if (mSelectList == null) {
            mSelectList = new ArrayList<>();
            for (int i = 0; i <= data.size(); i++) {
                mSelectList.add(false);
            }
        }
    }

    public void setSelect(int position) {
        int selectCount = 0;
        for (Boolean select : mSelectList) {
            if (select)
                selectCount++;
        }
        if (selectCount > 5) {
            ToastUtil.show(mContext, "최대 5개까지 선택가능합니다");
            return;
        }
        mSelectList.set(position, !mSelectList.get(position));
        notifyDataSetChanged();
    }

    public ArrayList<Store> getSelectItem() {
        ArrayList<Store> selectStore = new ArrayList<>();
        if (mSelectList != null) {
            for (int i = 0; i < mSelectList.size(); i++) {
                if (mSelectList.get(i)) {
                    selectStore.add(data.get(i));
                }
            }
        } else {
            initSelectList();
        }
        return selectStore;
    }
}
