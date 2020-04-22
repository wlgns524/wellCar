package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.model.response.notice.NoticeDetail;
import com.rightcode.wellcar.network.requester.notice.NoticeDetailRequester;
import com.rightcode.wellcar.network.responser.notice.NoticeDetailResponser;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_NOTICE_ID;

public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.tv_notice_date)
    TextView tv_notice_date;
    @BindView(R.id.tv_notice_title)
    TextView tv_notice_title;
    @BindView(R.id.tv_notice_contents)
    TextView tv_notice_contents;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        ButterKnife.bind(this);
        initialize();
    }


    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "공지사항");
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent() != null) {
            noticeDetail(getIntent().getIntExtra(EXTRA_NOTICE_ID, 0));
        }
    }

    private void noticeDetail(Integer id) {
        showLoading();
        NoticeDetailRequester noticeDetailRequester = new NoticeDetailRequester(NoticeDetailActivity.this);
        noticeDetailRequester.setId(id);

        request(noticeDetailRequester,
                success -> {
                    NoticeDetailResponser result = (NoticeDetailResponser) success;
                    if (result.getCode() == 200) {
                        initLayout(result.getNotice());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void initLayout(NoticeDetail data) {
        tv_notice_date.setText(data.getCreatedAt());
        tv_notice_title.setText(data.getTitle());
        tv_notice_contents.setText(data.getContent());
    }
}
