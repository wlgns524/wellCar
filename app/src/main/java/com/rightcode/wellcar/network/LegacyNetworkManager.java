package com.rightcode.wellcar.network;

import android.content.Context;

import androidx.annotation.NonNull;

import lombok.Getter;
import rx.functions.Func1;

public class LegacyNetworkManager extends AbstractNetworkManager {

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    private static final String COM_REAL_DOMAIN = "http://15.165.107.159:3030/";

    private static final String COM_QA_DOMAIN = "";

    private volatile static LegacyNetworkManager sInstance;

    @Getter
    private LegacyNetworkApi api;

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    private LegacyNetworkManager(@NonNull Context context) {
        super(context);
    }

    public static LegacyNetworkManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LegacyNetworkManager.class) {
                if (sInstance == null) {
                    sInstance = new LegacyNetworkManager(context);
                }
            }
        }
        return sInstance;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------
    public void destroy() {
        super.destroy();
        sInstance = null;
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------
    @Override
    protected String getQaDomain() {
        return COM_QA_DOMAIN;
    }

    @Override
    protected String getRealDomain() {
        return COM_REAL_DOMAIN;
    }

    @Override
    protected void callApiCreateFunc1(Func1<Class<?>, Object> func1) {
        api = (LegacyNetworkApi) func1.call(LegacyNetworkApi.class);
    }

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------
    public String getDomain() {
        return genDomain(LocaleServiceManager.getInstance(context).getServiceCountry());
    }
}