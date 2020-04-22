package com.rightcode.wellcar.Activity.CarRegist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarRegistVehicleRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarRegistYearRecyclerViewAdapter;
import com.rightcode.wellcar.Component.RecyclerViewOnClickListener;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.response.car.Car;
import com.rightcode.wellcar.network.requester.car.CarListRequester;
import com.rightcode.wellcar.network.responser.car.CarListResponser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_CAR_REGIST;

public class CarRegistYearActivity extends BaseActivity {

    @BindView(R.id.rv_year)
    RecyclerView rv_year;

    private TopFragment mTopFragment;
    private CarRegistYearRecyclerViewAdapter mCarRegistYearRecyclerViewAdapter;
    private ArrayList<Car> carList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_regist_year);

        ButterKnife.bind(this);
        initialize();
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "차량 등록하기");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(CarRegistYearActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_year.setLayoutManager(verticalLayoutManager);
        mCarRegistYearRecyclerViewAdapter = new CarRegistYearRecyclerViewAdapter(CarRegistYearActivity.this);
        rv_year.setAdapter(mCarRegistYearRecyclerViewAdapter);
        rv_year.addOnItemTouchListener(new RecyclerViewOnClickListener(CarRegistYearActivity.this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                hideKeyboard(view);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("year", carList.get(position).getModelYear());
                resultIntent.putExtra("carId", carList.get(position).getId());
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        }));
        if (getIntent() != null) {
            carList(getIntent().getStringExtra(EXTRA_CAR_REGIST));
        }
    }


    private void carList(String name) {
        showLoading();
        CarListRequester carListRequester = new CarListRequester(CarRegistYearActivity.this);
        carListRequester.setName(name);

        request(carListRequester,
                success -> {
                    CarListResponser result = (CarListResponser) success;
                    if (result.getCode() == 200) {
                        carList = result.getList();
                        mCarRegistYearRecyclerViewAdapter.setData(result.getList());
                        mCarRegistYearRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
