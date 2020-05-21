package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.AroundRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.Event;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.AroundSelectEvent;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.event.EventListRequester;
import com.rightcode.wellcar.network.requester.store.StoreListRequester;
import com.rightcode.wellcar.network.responser.event.EventListResponser;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AroundActivity extends BaseActivity {
    public static Timer around_timer;
    @Event(AroundSelectEvent.class)
    public void onAroundSelectEvent(AroundSelectEvent event) {
        if (selectList.contains(event.getOptionTitle())) {
            selectList.remove(event.getOptionTitle());
        } else {
            selectList.add(event.getOptionTitle());
        }
        storeList();
    }


    @BindView(R.id.rv_around)
    RecyclerView rv_around;

    private TopFragment mTopFragment;
    private AroundRecyclerViewAdapter mAroundRecyclerViewAdapter;
    private ArrayList<String> selectList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around);

        RxBus.register(this);
        ButterKnife.bind(this);
        initialize();
        storeList();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.unregister(this);
        if(around_timer != null) {
            around_timer.cancel();
            around_timer = null;
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        around_timer = new Timer();
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "내 주변업체");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(AroundActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_around.setLayoutManager(verticalLayoutManager);
        mAroundRecyclerViewAdapter = new AroundRecyclerViewAdapter(AroundActivity.this);
        mAroundRecyclerViewAdapter.setFm(getSupportFragmentManager());
        rv_around.setAdapter(mAroundRecyclerViewAdapter);
    }

    private void storeList() {
        showLoading();
        StoreListRequester storeListRequester = new StoreListRequester(AroundActivity.this);
        storeListRequester.setLatitude(MemberManager.getInstance(AroundActivity.this).getLocation().getLatitude());
        storeListRequester.setLongitude(MemberManager.getInstance(AroundActivity.this).getLocation().getLongitude());
        String[] Arr = new String[selectList.size()];
        Arr = selectList.toArray(Arr);
        storeListRequester.setDiff(Arr);

        request(storeListRequester,
                success -> {
                    StoreListResponser result = (StoreListResponser) success;
                    if (result.getCode() == 200) {
                        mAroundRecyclerViewAdapter.setData(result.getList());
                        mAroundRecyclerViewAdapter.notifyDataSetChanged();
                        eventList();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void eventList() {
        showLoading();
        EventListRequester eventListRequester = new EventListRequester(AroundActivity.this);
        eventListRequester.setLatitude(MemberManager.getInstance(AroundActivity.this).getLocation().getLatitude());
        eventListRequester.setLongitude(MemberManager.getInstance(AroundActivity.this).getLocation().getLongitude());

        request(eventListRequester,
                success -> {
                    EventListResponser result = (EventListResponser) success;
                    if (result.getCode() == 200) {
                        mAroundRecyclerViewAdapter.setEventData(result.getList());
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
