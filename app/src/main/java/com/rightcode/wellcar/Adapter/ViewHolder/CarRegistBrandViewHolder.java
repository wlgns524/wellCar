package com.rightcode.wellcar.Adapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rightcode.wellcar.Activity.CarRegist.CarRegistBrandActivity;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.brand.Brand;
import com.rightcode.wellcar.network.model.response.car.Car;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class CarRegistBrandViewHolder extends CommonRecyclerViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_brand)
    ImageView iv_brand;
    @BindView(R.id.tv_brand)
    TextView tv_brand;

    private Context mContext;
    private Brand data;

    public CarRegistBrandViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void onBind(Brand data) {
        this.data = data;
        Glide.with(mContext).load(data.getImage().getName())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(iv_brand);
        tv_brand.setText(data.getName());
    }

    @Override
    public void onClick(View v) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("brand", data.getName());
        resultIntent.putExtra("brandId", data.getId());
        ((Activity) mContext).setResult(RESULT_OK, resultIntent);
        ((Activity) mContext).finish();
    }
}
