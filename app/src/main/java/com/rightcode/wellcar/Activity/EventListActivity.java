package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.EventRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.event.EventListRequester;
import com.rightcode.wellcar.network.responser.event.EventListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventListActivity extends BaseActivity {

    @BindView(R.id.rv_event_list)
    RecyclerView rv_event_list;

    private TopFragment mTopFragment;
    private EventRecyclerViewAdapter mEventRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        ButterKnife.bind(this);
        initialize();
        eventList();
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "이벤트");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(EventListActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_event_list.setLayoutManager(verticalLayoutManager);
        mEventRecyclerViewAdapter = new EventRecyclerViewAdapter(EventListActivity.this);
        rv_event_list.setAdapter(mEventRecyclerViewAdapter);
    }

    private void eventList() {
        showLoading();
        EventListRequester eventListRequester = new EventListRequester(EventListActivity.this);
        eventListRequester.setLatitude(MemberManager.getInstance(EventListActivity.this).getLocation().getLatitude());
        eventListRequester.setLongitude(MemberManager.getInstance(EventListActivity.this).getLocation().getLongitude());
        eventListRequester.setLocation("top");

        request(eventListRequester,
                success -> {
                    EventListResponser result = (EventListResponser) success;
                    if (result.getCode() == 200) {
                        mEventRecyclerViewAdapter.setData(result.getList());
                        mEventRecyclerViewAdapter.notifyDataSetChanged();
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
