package com.rightcode.wellcar.network;


import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.rightcode.wellcar.ApiEnums;
import com.rightcode.wellcar.Features;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.PreferenceUtil;

import java.util.Locale;

import rx.Single;
import rx.functions.Action1;

public class LocaleServiceManager {
    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------
    private volatile static LocaleServiceManager sLocaleServiceManager;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    private Context context;

    /**
     * 서비스 국가 - 회원의 경우 회원이 프로필에서 설정한 국가. 비회원일 경우 캐시 된 서비스 국가나, 단말의 국가 설정을 따른다.
     */
    private ApiEnums.Country serviceCountry;

    //----------------------------------------------------------------------------------------------
    // action
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // get / set
    //----------------------------------------------------------------------------------------------


    /**
     * 서비스 국가가 한국인지 여부를 리턴한다.
     *
     * @return 서비스 국가가 한국이면 true, 아니면 false
     */
    public boolean isKoreaServiceCountry() {
        return serviceCountry == null ? false : serviceCountry.equals(ApiEnums.Country.KR);
    }


    /**
     * 서비스 국가를 리턴한다.
     *
     * @return 회원의 경우 회원이 프로필에서 설정한 국가. 비회원일 경우 캐시 된 서비스 국가나, 단말의 국가 설정을 따른다.
     */
    public ApiEnums.Country getServiceCountry() {
        return serviceCountry;
//        return serviceCountry == null ? ApiEnums.Country.US : serviceCountry;
    }

    //----------------------------------------------------------------------------------------------
    // abstract
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    private LocaleServiceManager(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
    }

    public static LocaleServiceManager getInstance(Context context) {
        if (sLocaleServiceManager == null) {
            synchronized (LocaleServiceManager.class) {
                if (sLocaleServiceManager == null) {
                    sLocaleServiceManager = new LocaleServiceManager(context);
                }
            }
        }
        return sLocaleServiceManager;
    }

    //----------------------------------------------------------------------------------------------
    // life cycle
    //----------------------------------------------------------------------------------------------
    public void destroy() {
        context = null;
        serviceCountry = null;

        sLocaleServiceManager = null;
    }

    //----------------------------------------------------------------------------------------------
    // override
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // event
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // public
    //----------------------------------------------------------------------------------------------
    public void updateServiceCountryAndPreference(ApiEnums.Country country) {
        Log.i(String.format(">> updateServiceCountryAndPreference( country : %s, currentServiceCountry : %s )", country, serviceCountry));

        if (country == null) {
            country = ApiEnums.Country.KR;
        }

        if (serviceCountry != null && serviceCountry.equals(country)) {
            return;
        }

        serviceCountry = country;
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.ServiceCountry, serviceCountry.name());

        // 서비스 국가에 따른 API 환경으로 변경
        LegacyNetworkManager.getInstance(context).build();
    }

    //----------------------------------------------------------------------------------------------
    // protected
    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    /**
     * 단말의 서비스 국가를 확인한다.
     *
     * @param context
     * @return 단말에서 읽을 수 있는 공팔리터 앱 지원 국가(한국, 미국, 중국, 싱가폴)를 전달하고, 값이 없으면 미국으로 기본 서비스 국가 전달하는 Single 객체
     */
    public static Single<ApiEnums.Country> fetchDeviceServiceCountry(Context context) {
        return Single.create(subscriber -> {
            Action1<ApiEnums.Country> doneAction1 = anEnum -> {
                if (subscriber != null && !subscriber.isUnsubscribed()) {
                    subscriber.onSuccess(anEnum);
                }
            };

            if (Features.SERVICE_COUNTRY != null) {
                doneAction1.call(Features.SERVICE_COUNTRY);
                Log.i(String.format("-- return( service country : %s )", Features.SERVICE_COUNTRY.toString()));
                return;
            }

            // 1. 캐시
            String country = PreferenceUtil.getInstance(context).get(PreferenceUtil.PreferenceKey.ServiceCountry, null);
            if (!TextUtils.isEmpty(country)) {
                doneAction1.call(ApiEnums.Country.getEnum(country));
                Log.i(String.format("-- return( service country : %s )", country));
                return;
            }

            // 2. Sim ISO
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                country = telephonyManager.getSimCountryIso(); // 가입 통신사 국가

                if (!TextUtils.isEmpty(country)) {
                    doneAction1.call(ApiEnums.Country.getEnum(country.toUpperCase()));
                    Log.i(String.format("-- return( service country : %s )", country));
                    return;
                }
            }

            // 3. Device Locale
            // 4. Default : KR
            doneAction1.call(ApiEnums.Country.getEnum(Locale.getDefault().getCountry()));
            Log.i(String.format("++ service country : %s", Locale.getDefault().getCountry()));
        });
    }

    //----------------------------------------------------------------------------------------------
    // inner class
    //----------------------------------------------------------------------------------------------
}