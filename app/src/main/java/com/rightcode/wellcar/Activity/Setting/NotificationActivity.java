package com.rightcode.wellcar.Activity.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.Nullable;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.notification.NotificationDetailRequester;
import com.rightcode.wellcar.network.requester.notification.NotificationUpdateRequester;
import com.rightcode.wellcar.network.responser.notification.NotificationDetailResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity {

    @BindView(R.id.swt_notification)
    Switch swt_notification;
    @BindView(R.id.swt_welltalk_notification)
    Switch swt_welltalk_notification;

    private TopFragment mTopFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ButterKnife.bind(this);
        initialize();
        notificationDetail();
    }

    @OnClick({R.id.swt_notification, R.id.swt_welltalk_notification})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.swt_notification:
                notificationUpdate(swt_notification.isChecked(), swt_welltalk_notification.isChecked());
                break;
            case R.id.swt_welltalk_notification: {
                notificationUpdate(swt_notification.isChecked(), swt_welltalk_notification.isChecked());
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "푸시 알림");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
    }

    private void notificationDetail() {
        showLoading();
        NotificationDetailRequester notificationDetailRequester = new NotificationDetailRequester(NotificationActivity.this);
        notificationDetailRequester.setNotificationToken(FirebaseInstanceId.getInstance().getToken());

        request(notificationDetailRequester,
                success -> {
                    NotificationDetailResponser result = (NotificationDetailResponser) success;
                    if (result.getCode() == 200) {
                        swt_notification.setChecked(result.getNotification().getActive());
                        swt_welltalk_notification.setChecked(result.getNotification().getChatActive());
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void notificationUpdate(Boolean check, Boolean chatActive) {
        showLoading();
        NotificationUpdateRequester notificationUpdateRequester = new NotificationUpdateRequester(NotificationActivity.this);
        notificationUpdateRequester.setNotificationToken(FirebaseInstanceId.getInstance().getToken());
        notificationUpdateRequester.setActive(check);
        notificationUpdateRequester.setChatActive(chatActive);

        request(notificationUpdateRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(NotificationActivity.this, "변경되었습니다");
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
