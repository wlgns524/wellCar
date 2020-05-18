package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.ImageViewActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_IMAGE;


public class ReviewImageViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_review_image)
    ImageView iv_review_image;

    private Context mContext;
    private String imageUrl;

    public ReviewImageViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(String imageUrl) {
        this.imageUrl = imageUrl;
        Glide.with(mContext)
                .load(imageUrl)
                .centerCrop()
                .into(iv_review_image);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ImageViewActivity.class);
        intent.putExtra(EXTRA_IMAGE, imageUrl);
        startActivity(intent);
    }
}
