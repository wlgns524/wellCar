package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.Image;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CompanyImageViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.iv_company_image)
    ImageView iv_company_image;

    private Context mContext;

    public CompanyImageViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(Image data) {
        Log.d(data);
        Glide.with(mContext)
                .load(data.getName())
                .into(iv_company_image);
    }
}
