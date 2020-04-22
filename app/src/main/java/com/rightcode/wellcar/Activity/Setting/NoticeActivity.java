package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.CompanyRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.NoticeRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.notice.NoticeListRequester;
import com.rightcode.wellcar.network.responser.notice.NoticeListResponser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends BaseActivity {

    @BindView(R.id.rv_notice)
    RecyclerView rv_notice;

    private TopFragment mTopFragment;
    private NoticeRecyclerViewAdapter mNoticeRecyclerViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        ButterKnife.bind(this);
        initialize();
        noticeList();
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "공지사항");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(NoticeActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_notice.setLayoutManager(verticalLayoutManager);
        mNoticeRecyclerViewAdapter = new NoticeRecyclerViewAdapter(NoticeActivity.this);
        rv_notice.setAdapter(mNoticeRecyclerViewAdapter);
    }

    private void noticeList() {
        NoticeListRequester noticeListRequester = new NoticeListRequester(NoticeActivity.this);

        request(noticeListRequester,
                success -> {
                    NoticeListResponser result = (NoticeListResponser) success;
                    if (result.getCode() == 200) {
                        mNoticeRecyclerViewAdapter.setData(result.getNotices());
                        mNoticeRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
