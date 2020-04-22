package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.ReviewWriteActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ReviewImageRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.ReviewRemoveEvent;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_DATA;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_ID;


public class ReviewManagementViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_store_name)
    TextView tv_store_name;
    @BindView(R.id.tv_user_nickname)
    TextView tv_user_nickname;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.rv_review_image)
    RecyclerView rv_review_image;
    @BindView(R.id.tv_review_content)
    TextView tv_review_content;
    @BindView(R.id.tv_review_date)
    TextView tv_review_date;

    private Context mContext;
    private ReviewImageRecyclerViewAdapter mReviewImageRecyclerViewAdapter;
    private StoreReview data;

    public ReviewManagementViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(StoreReview data) {
        this.data = data;
        tv_store_name.setText(data.getStore().getName());
        tv_user_nickname.setText(data.getUser().getGeneral().getNickname());
        tv_grade.setText(data.getRate().toString());
        tv_review_content.setText(data.getContent());
        tv_review_date.setText(data.getCreatedAt());

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv_review_image.setLayoutManager(verticalLayoutManager);
        mReviewImageRecyclerViewAdapter = new ReviewImageRecyclerViewAdapter(mContext);
        mReviewImageRecyclerViewAdapter.setData(data.getStoreReviewImages());
        rv_review_image.setAdapter(mReviewImageRecyclerViewAdapter);
    }

    @OnClick({R.id.tv_review_modify, R.id.tv_review_delete})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_review_modify: {
                showDialog("modify", data.getId());
                break;
            }
            case R.id.tv_review_delete: {
                showDialog("delete", data.getId());
                break;
            }
        }
    }

    private void showDialog(String type, Integer reviewId) {
        CommonDialog commonDialog = new CommonDialog(mContext);
        if (type.equals("delete")) {
            commonDialog.setMessage("리뷰를 삭제하시겠습니까 ?");
            commonDialog.setPositiveButton("확인", ok -> {
                RxBus.send(new ReviewRemoveEvent(reviewId));
                commonDialog.dismiss();
            });
        } else if (type.equals("modify")) {
            commonDialog.setMessage("리뷰를 수정하시겠습니까 ?");
            commonDialog.setPositiveButton("확인", ok -> {
                Intent intent = new Intent(mContext, ReviewWriteActivity.class);
                intent.putExtra(EXTRA_REVIEW_DATA, data);
                intent.putExtra(EXTRA_REVIEW_ID, -1);
                startActivity(intent);
                commonDialog.dismiss();
            });
        }
        commonDialog.setNegativeButton("취소", cancel -> {
            commonDialog.dismiss();
        });
        commonDialog.show();
    }
}
