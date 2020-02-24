package com.rightcode.wellcar.Adapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.NetworkBridge;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.CommonApiRequester;

import rx.functions.Action1;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------

    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 500;
    private Context mContext;
    private NetworkBridge networkBridge;

    public interface onBind {
    }

    //----------------------------------------------------------------------------------------------
    // action
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // get / set
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // abstract
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------

    public CommonRecyclerViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        mContext = context;
        networkBridge = new NetworkBridge();
    }


    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    public void startActivity(Intent intent) {
        // 더블 탭으로 인한 이중 액션 막기
//        long now = System.currentTimeMillis();
//        if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
//            return;
//        }
//        mLastClickTime = now;
        mContext.startActivity(intent);
    }
    //----------------------------------------------------------------------------------------------
    // event
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------


    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------


    protected void request(CommonApiRequester requester, Action1<? super
            CommonResult> successAction, Action1<? super CommonResult> failAction) {
        if (networkBridge != null) {
            networkBridge.request(requester, successAction, failAction);
        } else {
            Log.d("networkBridge null");
        }
    }
    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------
}