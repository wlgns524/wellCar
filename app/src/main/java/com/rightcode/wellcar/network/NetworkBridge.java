package com.rightcode.wellcar.network;


import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.requester.CommonApiRequester;

import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;

public class NetworkBridge {
    private ArrayList<CommonApiRequester> requesters = new ArrayList<>();

    public synchronized void request(CommonApiRequester requester, Action1<? super CommonResult> successAction, Action1<? super CommonResult> failAction) {
        if (requester == null) {
            Log.d("return( requester is null )");
            return;
        }

        if (requester.getContext() == null) {
            remove(requester);
            return;
        }

        synchronized (NetworkBridge.class) {
            requester.request(
                    success -> {
                        remove(requester);
                        if (successAction != null) {
                            Observable.just(success).subscribe(successAction);
                        }
                    },
                    fail -> {
                        remove(requester);
                        if (failAction != null) {
                            Observable.just(fail).subscribe(failAction);
                        }
                    }
            );

            requesters.add(requester);
        }
    }

    public void remove(CommonApiRequester requester) {
        if (requesters == null || requester == null) {
            return;
        }
        synchronized (NetworkBridge.class) {
            requesters.remove(requester);
        }
    }

    public void clear() {
        synchronized (NetworkBridge.class) {
            if (requesters != null) {
                for (CommonApiRequester requester : requesters) {
                    requester.cancel();
                }
                requesters.clear();
                requesters = null;
            }
        }
    }
}