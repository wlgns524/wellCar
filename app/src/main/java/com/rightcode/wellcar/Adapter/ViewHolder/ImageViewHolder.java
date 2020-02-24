package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.rv_image)
    ImageView rv_image;

    private Context mContext;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public ImageViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
    }

    public void onBind(String data) {
        Glide.with(mContext).load(new File(data))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(rv_image);
    }
}
