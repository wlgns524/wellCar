package com.rightcode.wellcar.Activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.network.requester.version.VersionRequester;
import com.rightcode.wellcar.network.requester.visitor.VisitorRequester;
import com.rightcode.wellcar.network.responser.version.VersionResponser;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class IntroActivity extends BaseActivity {

    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this);
        version();
        visitor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    private void timer() {
        subscription = Observable.interval(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    public void call(Long aLong) {
                        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishWithAnim();
                    }
                });
    }

    private void version() {
        showLoading();
        VersionRequester versionRequester = new VersionRequester(IntroActivity.this);

        request(versionRequester,
                success -> {
                    VersionResponser result = (VersionResponser) success;
                    if (CommonUtil.getVersionCode(IntroActivity.this) <= result.getVersion().getAndroid()) {
                        checkPermission();
                    } else {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void visitor() {
        VisitorRequester visitorRequester = new VisitorRequester(IntroActivity.this);

        request(visitorRequester,
                success -> {

                }, fail -> {

                });
    }

    private void checkPermission() {
        Dexter.withActivity(IntroActivity.this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
//                        }
                        if (report.areAllPermissionsGranted()) {
                            timer();
                        } else {
                            Toast.makeText(IntroActivity.this, "[웰카]이용을 위해서카 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        Toast.makeText(IntroActivity.this, "[웰카]이용을 위해서 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                        Intent appDetail = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                        appDetail.addCategory(Intent.CATEGORY_DEFAULT);
                        appDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(appDetail);
                    }
                }).check();
    }
}
