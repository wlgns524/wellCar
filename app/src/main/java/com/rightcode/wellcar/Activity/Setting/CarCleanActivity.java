package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CarCleanRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.ReviewWriteEvent;
import com.rightcode.wellcar.RxJava.RxEvent.SelectEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.ticketHistory.TicketHistoryListRequester;
import com.rightcode.wellcar.network.responser.ticketHistory.TicketHistoryListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarCleanActivity extends BaseActivity {

    @Event(ReviewWriteEvent.class)
    public void onSelectEvent(ReviewWriteEvent event) {
        ticketHistoryList();
    }

    @BindView(R.id.rv_car_clean)
    RecyclerView rv_car_clean;

    private TopFragment mTopFragment;
    private CarCleanRecyclerViewAdapter mCarCleanRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_clean);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
        ticketHistoryList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "세차 이용 관리");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(CarCleanActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_car_clean.setLayoutManager(verticalLayoutManager);
        mCarCleanRecyclerViewAdapter = new CarCleanRecyclerViewAdapter(CarCleanActivity.this);
        rv_car_clean.setAdapter(mCarCleanRecyclerViewAdapter);
    }


    private void ticketHistoryList() {
        showLoading();
        TicketHistoryListRequester ticketHistoryListRequester = new TicketHistoryListRequester(CarCleanActivity.this);

        request(ticketHistoryListRequester,
                success -> {
                    TicketHistoryListResponser result = (TicketHistoryListResponser) success;
                    if (result.getCode() == 200) {
                        mCarCleanRecyclerViewAdapter.setData(result.getList());
                        mCarCleanRecyclerViewAdapter.notifyDataSetChanged();
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
