package com.rightcode.wellcar.Interface;


import android.content.Intent;
import android.view.View;

import rx.functions.Action1;

public interface CommonViewInterface {

    void showLoading();

    void hideLoading();

    boolean isLoading();

    void hideKeyboard(View v);

    void showKeyboard(View v);

    void startActivity(Intent intent);

    void finishWithAnim();

    void showNeedToUpdateServerErrorDialog(String message);

    void showCheckingServiceServerErrorDialog(String message);

    void showServerErrorDialog(String message);

    void showServerErrorDialog(String message, Action1<Void> action);


}
