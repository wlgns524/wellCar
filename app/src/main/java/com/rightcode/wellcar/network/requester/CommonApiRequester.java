package com.rightcode.wellcar.network.requester;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import com.rightcode.wellcar.Features;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.error.NetworkErrorCode;
import com.rightcode.wellcar.network.error.ServerResultCode;
import com.rightcode.wellcar.network.model.CommonResult;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.os.Build.UNKNOWN;

public abstract class CommonApiRequester {
    private static final int RETRY_COUNT = 1;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    protected Context context;
    private Call api;
    private Callback<?> callback;
    private int retry = 0;

    private Action1<? super CommonResult> successAction;
    private Action1<? super CommonResult> failAction;

    //----------------------------------------------------------------------------------------------
    // abstract
    //----------------------------------------------------------------------------------------------
    abstract protected Call genApi() throws Exception;

    abstract protected Callback<?> genCallback();

    abstract protected CommonResult extractResult(int responseCode, Object responseBody);

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    public CommonApiRequester(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------
    public void request(Action1<? super CommonResult> successAction, Action1<? super CommonResult> failAction) {
        this.successAction = successAction;
        this.failAction = failAction;

        Observable.just(null).onBackpressureDrop().subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(
                (Void) -> {
                    try {
                        api = genApi();
                    } catch (Exception e) {
                        handleThrowable(e, NetworkErrorCode.MAKE_REQUEST_DATA_THROWABLE, failAction);
                        return;
                    }

                    callback = genCallback();
                    api.enqueue(callback);
                },

                throwable -> {
                    handleThrowable(throwable, NetworkErrorCode.REQUEST_THROWABLE, failAction);
                });
    }

    public void cancel() {
        if (api == null) {
            return;
        }
        api.cancel();
    }

    public Context getContext() {
        return context;
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------
    protected void handleResponse(Call<?> call, Response<?> response) {
        if (response == null || response.raw() == null) {
            Log.w("-- return( response is invalid )");
            return;
        }

        if (call == null) {
            Log.i("-- return( call is null )");
            return;
        }
        if (call.isCanceled()) {
            Log.d("-- return( call is canceled )");
            return;
        }

        // check http error
        if (!response.isSuccessful() && response.code() != HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
            response.errorBody().close();
            performServerFail(genHttpErrorResult(response.raw()));
            return;
        }

        // gen result
        final CommonResult result = extractResult(response.code(), response.body());
        if (result == null) {
            performServerFail(null);
            return;
        }


        if (result.getResult()) { // TRY 상세 화면에서 BUY 상세 화면으로 리다이렉트 시키는 케이스는 에러 외 데이터를 접근해야 하므로 code:0000 아니어도 성공으로 예외 처리(julie, 2017. 11. 30)
            onBeforeDispatchSubscription(result);
            Observable.just(result).observeOn(AndroidSchedulers.mainThread()).subscribe(successAction);
        } else {
            performServerFail(result);
        }
    }

    protected void handleFailure(Call<?> call, Throwable t) {
        if (call == null) {
            Log.i("-- return( call is null )");
            return;
        }

        if (call.isCanceled()) {
            Log.d("-- return( call is canceled )");
            return;
        }

        performServerFail(genNetworkExceptionResult(t));
    }

    protected void onBeforeDispatchSubscription(CommonResult commonResult) {
    }

    protected static RequestBody toRequestBody(String value) {
        if (value == null) {
            return null;
        }
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------
    private void retry() {
        Log.d();
        if (api != null) {
            retry++;
            api.clone().enqueue(callback);
        }
    }

    private void handleThrowable(Throwable t, NetworkErrorCode error, Action1<? super CommonResult> failAction) {
        if (Features.TEST_ONLY && Features.SHOW_NETWORK_LOG) {
            Log.e(t);
        }

        CommonResult result = new CommonResult();
        result.setCode(error.getMessageResId());
        result.setResult(false);
        result.setResultMsg(String.format(context.getString(error.getMessageResId()), result.getCode()));

        performServerFail(result);
    }

    private CommonResult genHttpErrorResult(okhttp3.Response response) {
        if (Features.TEST_ONLY && Features.SHOW_NETWORK_LOG) {
            Log.e(String.format("++ Http Result : %s", response.message()));
        }

        CommonResult result = new CommonResult();
        result.setCode(ServerResultCode.getEnum(UNKNOWN).getResultCode());
        result.setResult(false);
        result.setResultMsg(String.format(context.getString(R.string.server_connect_error_message), String.valueOf(response.code())));

        return result;
    }

    private CommonResult genNetworkExceptionResult(Throwable t) {
        if (Features.TEST_ONLY && Features.SHOW_NETWORK_LOG) {
            Log.e(t);
        }

        NetworkErrorCode networkErrorCode;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            networkErrorCode = NetworkErrorCode.IO_NOT_ACTIVE_NETWORK;
        } else if (!activeNetworkInfo.isConnected()) {
            networkErrorCode = NetworkErrorCode.IO_NETWORK_DISCONNECTED;
        } else {
            if (t instanceof SocketTimeoutException) {
                networkErrorCode = NetworkErrorCode.SOCKET_TIME_OUT;
            } else if (t instanceof UnknownHostException) {
                networkErrorCode = NetworkErrorCode.UNKNOWN_HOST;
            } else if (t instanceof IOException) {
                networkErrorCode = NetworkErrorCode.IO;
            } else if (t instanceof IllegalArgumentException) {
                networkErrorCode = NetworkErrorCode.ILLEGAL_ARGUMENT;
            } else {
                networkErrorCode = NetworkErrorCode.EXCEPTION;
            }
        }

        CommonResult result = new CommonResult();
        result.setCode(networkErrorCode.getMessageResId());
        result.setResult(false);
        result.setResultMsg(String.format(context.getString(networkErrorCode.getMessageResId()), result.getCode()));

        return result;
    }

    private void performServerFail(CommonResult result) {
        Log.d(String.format(">> performServerFail( %s )", result == null ? "performServerFairesult is null" : result.toString()));

        if (result == null) {
            result = new CommonResult();
//            result.setErrorType(ErrorType.NETWORK_EXCEPTION);
            result.setCode(ServerResultCode.getEnum(UNKNOWN).getResultCode());
        }

        onBeforeDispatchSubscription(result);

        if (TextUtils.isEmpty(result.getResultMsg())) {
            result.setResultMsg(context.getString(R.string.unknown_error));
        }

        Observable.just(result).observeOn(AndroidSchedulers.mainThread()).subscribe(failAction);
    }
}