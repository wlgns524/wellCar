package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.TalkDetailCustomerActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.MoneyHelper;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoom;
import com.rightcode.wellcar.network.model.response.item.Item;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_NAME;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_TALK_ID;


public class TalkCustomerViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_talk_date)
    TextView tv_talk_date;
    @BindView(R.id.tv_chats)
    TextView tv_chats;
    @BindView(R.id.tv_talk_count)
    TextView tv_talk_count;
    @BindView(R.id.tv_estimate_price)
    TextView tv_estimate_price;
    @BindView(R.id.fl_item_list)
    TagFlowLayout fl_item_list;

    private Context mContext;
    private LayoutInflater itemInflater;
    private TagAdapter itemAdapter;
    private ChatRoom data;

    public TalkCustomerViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(ChatRoom data) {
        this.data = data;
        /**
         * 유저정보 불러오지 말기
         */
        Glide.with(mContext)
                .load(data.getStore() != null ? data.getStore().getThumbnail().getName() : "")
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(iv_company_image);
        tv_company_name.setText(data.getStore().getName());

        tv_talk_date.setText(data.getCreatedAt());
        tv_chats.setText(data.getChats().get(data.getChats().size() - 1) != null ? data.getChats().get(data.getChats().size() - 1) : "채팅을 시작하세요!");
        if (data.getViewCount() > 0) {
            tv_talk_count.setText(data.getViewCount().toString());
            tv_talk_count.setVisibility(View.VISIBLE);
        } else {
            tv_talk_count.setVisibility(View.GONE);
        }
        tv_estimate_price.setText(MoneyHelper.getUsaUnit(data.getEstimateStore().getPrice()));
        if (data.getEstimateStore().getEstimate() != null)
            initItemLayout(data.getEstimateStore().getEstimate().getItems());
    }


    private void initItemLayout(ArrayList<Item> itemList) {
        // 데이터 추가
        int size = 0;
        Item[] mVals = new Item[itemList.size()];
        for (Item item : itemList) {
            mVals[size++] = item;
        }
        itemInflater = LayoutInflater.from(mContext);
        itemAdapter = new TagAdapter<Item>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, Item item) {
                final View view = itemInflater.inflate(R.layout.item_flowlayout_text, fl_item_list, false);
                TextView tv_flowlayout = (TextView) view.findViewById(R.id.tv_flowlayout);

                tv_flowlayout.setText(String.format("%s(%s)", item.getDiff(), item.getItemBrand().getName()));
                return view;
            }

        };
        fl_item_list.setAdapter(itemAdapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, TalkDetailCustomerActivity.class);
        intent.putExtra(EXTRA_COMPANY_NAME, data.getStore().getName());
        intent.putExtra(EXTRA_TALK_ID, data.getId());
        intent.putExtra(EXTRA_COMPANY_ID, data.getStore().getStoreId());
        startActivity(intent);
    }
}
