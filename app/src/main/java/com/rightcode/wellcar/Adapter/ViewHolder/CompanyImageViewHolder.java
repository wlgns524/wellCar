package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.ImageViewActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.Image;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_IMAGE;


public class CompanyImageViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;

    private Context mContext;
    private String imageUrl;

    public CompanyImageViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(Image data) {
        imageUrl = data.getName();
        Glide.with(mContext)
                .load(data.getName())
                .into(iv_company_image);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, ImageViewActivity.class);
        intent.putExtra(EXTRA_IMAGE, imageUrl);
        startActivity(intent);
    }
}
