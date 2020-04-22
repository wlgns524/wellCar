package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.network.model.response.car.Car;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CarRegistYearViewHolder extends CommonRecyclerViewHolder {

    @BindView(R.id.tv_car_register_year)
    TextView tv_car_register_year;

    private Context mContext;

    public CarRegistYearViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;
        ButterKnife.bind(this, itemView);

    }

    public void onBind(Car data) {
        tv_car_register_year.setText(data.getModelYear());
    }
}
