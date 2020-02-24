package com.rightcode.wellcar.network.requester;

import android.content.Context;

import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.LegacyNetworkManager;
import com.rightcode.wellcar.network.model.CommonResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class AbstractRequester extends CommonApiRequester {
    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    protected LegacyNetworkManager networkManager;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    public AbstractRequester(Context context) {
        super(context);
        networkManager = LegacyNetworkManager.getInstance(this.context);
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------
    protected Callback<?> genCallback() {
        return new Callback<CommonResult>() {
            @Override
            public void onResponse(Call<CommonResult> call, Response<CommonResult> response) {
                handleResponse(call, response);
                Log.d(call);
            }

            @Override
            public void onFailure(Call<CommonResult> call, Throwable t) {
                handleFailure(call, t);
                Log.d(call);
            }
        };
    }

    protected CommonResult extractResult(int responseCode, Object responseBody) {
        if (responseBody != null && responseBody instanceof CommonResult) {
            CommonResult result = (CommonResult) responseBody;
            if (result == null) {
                result = new CommonResult();
                result.setCode(9999);
                result.setResult(false);
                result.setResultMsg(getContext().getResources().getString(R.string.unknown_error));
            }
            return result;
        }
        return null;
    }
}