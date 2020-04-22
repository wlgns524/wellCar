package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.event.EventDetail;
import com.rightcode.wellcar.network.requester.event.EventDetailRequester;
import com.rightcode.wellcar.network.responser.event.EventDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_EVENT_ID;

public class EventDetailActivity extends BaseActivity {

    @BindView(R.id.iv_event_detail)
    ImageView iv_event_detail;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ButterKnife.bind(this);
        initialize();
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

        if (getIntent() != null) {
            eventDetail(getIntent().getIntExtra(EXTRA_EVENT_ID, -1));
        }
    }

    private void eventDetail(Integer id) {
        showLoading();
        EventDetailRequester eventDetailRequester = new EventDetailRequester(EventDetailActivity.this);
        eventDetailRequester.setId(id);

        request(eventDetailRequester,
                success -> {
                    EventDetailResponser result = (EventDetailResponser) success;
                    if (result.getCode() == 200) {
                        initLayout(result.getEvent());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout(EventDetail data) {
        Glide.with(EventDetailActivity.this)
                .load(data.getImage())
                .into(iv_event_detail);
    }
}