package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.Setting.EstimateCustomerDetailActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EstimateCustomerItemRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.EstimateRemoveEvent;
import com.rightcode.wellcar.network.model.response.estimate.Estimate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ESTIMATE_ID;


public class EstimateCustomerViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.rv_estimate_customer_item)
    RecyclerView rv_estimate_customer_item;
    @BindView(R.id.tv_estimate_detail)
    TextView tv_estimate_detail;

    private Context mContext;
    private EstimateCustomerItemRecyclerViewAdapter mEstimateCustomerItemRecyclerViewAdapter;
    private Estimate data;

    public EstimateCustomerViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }


    public void onBind(Estimate data) {
        this.data = data;
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv_estimate_customer_item.setLayoutManager(verticalLayoutManager);
        mEstimateCustomerItemRecyclerViewAdapter = new EstimateCustomerItemRecyclerViewAdapter(mContext);
        mEstimateCustomerItemRecyclerViewAdapter.setData(data);
        rv_estimate_customer_item.setAdapter(mEstimateCustomerItemRecyclerViewAdapter);

//        tv_estimate_detail.setText(String.format("%d개의 업체 견적 확인", data.getItems().size()));
    }

    @OnClick({R.id.iv_trash_box, R.id.tv_estimate_detail})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_trash_box: {
                CommonDialog commonDialog = new CommonDialog(mContext);
                commonDialog.setMessage("삭제하시겠습니까 ?");
                commonDialog.setPositiveButton("확인", ok -> {
                    RxBus.send(new EstimateRemoveEvent(data.getId()));
                    commonDialog.dismiss();
                });
                commonDialog.setNegativeButton("취소", cancel -> {
                    commonDialog.dismiss();
                });
                commonDialog.show();
                break;
            }
            case R.id.tv_estimate_detail: {
                Intent intent = new Intent(mContext, EstimateCustomerDetailActivity.class);
                intent.putExtra(EXTRA_ESTIMATE_ID, data.getId());
                startActivity(intent);
                break;
            }
        }
    }
}
