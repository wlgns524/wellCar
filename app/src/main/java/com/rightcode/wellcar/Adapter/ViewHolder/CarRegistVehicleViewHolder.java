package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.car.Car;
import com.rightcode.wellcar.network.model.response.store.Store;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarRegistVehicleViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_car_register_vehicle)
    TextView tv_car_register_vehicle;

    private Context mContext;

    public CarRegistVehicleViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);

    }

    public void onBind(Car data) {
        tv_car_register_vehicle.setText(data.getName());
    }
}
