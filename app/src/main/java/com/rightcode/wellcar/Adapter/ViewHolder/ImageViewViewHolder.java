package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.rv_image)
    ImageView rv_image;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ImageViewViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(String data) {
        Glide.with(mContext).load(data)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(rv_image);
    }
}
