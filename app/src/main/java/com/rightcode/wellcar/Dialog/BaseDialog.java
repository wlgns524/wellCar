package com.rightcode.wellcar.Dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.NetworkBridge;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.CommonApiRequester;

import rx.functions.Action1;


public class BaseDialog extends Dialog {

    public int dialogId;
    private Context mContext;
    private NetworkBridge networkBridge;
    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkBridge = new NetworkBridge();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (networkBridge != null) {
            networkBridge.clear();
            networkBridge = null;
        }
    }

    public BaseDialog(Context context) {
        this(context, true);
        mContext = context;
    }

    public BaseDialog(Context context, boolean animation) {
        this(context, animation, true);
        mContext = context;
    }

    public BaseDialog(Context context, boolean animation, boolean background) {
        super(context, R.style.Dialog_AppTheme_Transparent_TransparentStatusBar);
        mContext = context;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (animation) {
            lp.windowAnimations = R.style.CommonDialogAnimation;
        }
        if (background) {
            lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lp.dimAmount = 0.0f;
            getWindow().setBackgroundDrawable(new ColorDrawable(0x33222222));
        }
    }

    public int getDialogId() {
        return dialogId;
    }

    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    public void startActivity(Intent intent) {
        // 더블 탭으로 인한 이중 액션 막기
        long now = System.currentTimeMillis();
        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
            return;
        }
        mLastClickTime = now;
        mContext.startActivity(intent);

    }

    @Override
    public void show() {
        if (getContext() instanceof ContextWrapper && ((ContextWrapper) getContext()).getBaseContext() instanceof Activity) {
            Activity activity = (Activity) ((ContextWrapper) getContext()).getBaseContext();
            if (activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
        }

        super.show();
    }

    protected void setStatusBarMargin(View topView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) topView.getLayoutParams();
                params.topMargin = getContext().getResources().getDimensionPixelSize(resourceId);
                topView.setLayoutParams(params);
            }
        }
    }


    protected void request(CommonApiRequester requester, Action1<? super CommonResult> successAction, Action1<? super CommonResult> failAction) {
        if(networkBridge == null) {
            networkBridge = new NetworkBridge();
        }

        if (networkBridge != null) {
            networkBridge.request(requester, successAction, failAction);
        } else {
            Log.d("networkBridge null");
        }
    }
}
