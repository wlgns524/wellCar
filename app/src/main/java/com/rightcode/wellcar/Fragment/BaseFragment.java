package com.rightcode.wellcar.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Interface.CommonViewInterface;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.NetworkBridge;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.CommonApiRequester;

import rx.functions.Action1;


public class BaseFragment extends Fragment implements CommonViewInterface {


    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private NetworkBridge networkBridge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkBridge = new NetworkBridge();
    }

    @Override
    public void onDestroy() {
        if (networkBridge != null) {
            networkBridge.clear();
            networkBridge = null;
        }

        super.onDestroy();

        Glide.get(getContext()).clearMemory();
        CommonUtil.unbindDrawables(getView());
    }

    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideLoading();
        }
    }

    @Override
    public boolean isLoading() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).isLoading();
        }
        return false;
    }

    @Override
    public void hideKeyboard(View v) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).hideKeyboard(v);
        }
    }

    @Override
    public void showKeyboard(View v) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showKeyboard(v);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (getActivity() instanceof BaseActivity) {
            // 더블 탭으로 인한 이중 액션 막기
//            long now = System.currentTimeMillis();
//            if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
//                return;
//            }
//            mLastClickTime = now;
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void finishWithAnim() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).finishWithAnim();
        }
    }

    @Override
    public void showNeedToUpdateServerErrorDialog(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showNeedToUpdateServerErrorDialog(message);
        }
    }

    @Override
    public void showCheckingServiceServerErrorDialog(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showCheckingServiceServerErrorDialog(message);
        }
    }

    @Override
    public void showServerErrorDialog(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showServerErrorDialog(message);
        }
    }

    @Override
    public void showServerErrorDialog(String message, Action1<Void> action) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showServerErrorDialog(message);
        }
    }

    public boolean handleServerError(CommonResult commonResult) {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).handleServerError(commonResult);
        }
        return false;
    }

    protected void request(CommonApiRequester requester, Action1<? super CommonResult> successAction, Action1<? super CommonResult> failAction) {
        if(networkBridge==null){
            Log.d("network bridge null");
        }
        if (networkBridge != null) {
            networkBridge.request(requester, success -> {
                if (getActivity() == null) {
                    return;
                }

                if (successAction != null) {
                    successAction.call(success);
                }
            }, error -> {
                if (getActivity() == null) {
                    return;
                }

                if (failAction != null) {
                    failAction.call(error);
                }
            });
        }
    }
}
