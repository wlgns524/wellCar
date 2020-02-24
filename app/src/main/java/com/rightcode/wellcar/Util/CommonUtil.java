package com.rightcode.wellcar.Util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rightcode.wellcar.R;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;


public class CommonUtil {


    private Pattern passwordPattern;

    public static Boolean isIdValid(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-z][a-zA-z0-9][0-9a-zA-Z]{7,15}$");
        if (pattern.matcher(str).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isPwValid(String str) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{8,16}$");
        if (pattern.matcher(str).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean isPwNumberValid(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,16}$");
        if (pattern.matcher(str).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void showKeyPad(View v) {
        if (v == null) {
            return;
        }
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

    public static void hideKeyPad(View v) {
        if (v == null) {
            return;
        }
        v.clearFocus();
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String timeToString(Context context, long time) {
        if (time < 60) {
            return context.getString(R.string.a_moment_ago);
        } else if (time < 60 * 60) {
            return String.format(context.getString(R.string.minutes_ago), time / 60);
        } else if (time < 60 * 60 * 24) {
            return String.format(context.getString(R.string.hours_ago), time / 60 / 60);
        } else if (time < 60 * 60 * 24 * 7) {
            return String.format(context.getString(R.string.days_ago), time / 60 / 60 / 24);
        } else if (time < 60 * 60 * 24 * 7 * 5) {
            return String.format(context.getString(R.string.weeks_ago), time / 60 / 60 / 24 / 7);
        } else if (time < 60 * 60 * 24 * 365) {
            return String.format(context.getString(R.string.months_ago), time / 60 / 60 / 24 / 30);
        } else {
            return String.format(context.getString(R.string.years_ago), time / 60 / 60 / 24 / 365);
        }
    }


    public static String distanceToString(double distance) {
        if (distance < 1000) { // 1000미만은 m로 표시
            return String.format("%dm", ((int) distance));
        } else if (distance < 10000) { // 10km 미만은 소수점 두자리 까지 km로 표시
            boolean isInteger = distance % 1000 == 0;
            return isInteger ? String.format("%dkm", (int) (distance / 1000)) : String.format("%.2fkm", (distance / 1000));
        } else { //  10km 이상은 소수점 값없이 km 만 표시
            return String.format("%dkm", ((int) (distance / 1000)));
        }
    }

    public static String randomNum() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return String.valueOf(n);
    }

    public static boolean isEmpty(String string) {
        if (string == null) return true;
        String str = string.trim();
        return "null".equals(str) || str.length() == 0;
    }

    public static boolean isGooglePlayStoreApp(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo("com.android.vending", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionName;
    }

    public static String getVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf(pi.versionCode);
    }

    public static void unbindDrawables(View view) {
        if (view == null) {
            return;
        }

        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
            if (view.getParent() == null || !(view.getParent() instanceof SwipeRefreshLayout)) { // onDetatch() 호출 될 때 mCircleView.getBackground().setAlpha() 코드 때문에 제외
                view.setBackground(null);
            }
        }

        if (view instanceof ImageView && ((ImageView) view).getDrawable() != null) {
            ((ImageView) view).getDrawable().setCallback(null);
            ((ImageView) view).setImageDrawable(null);
        }

        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }

        if (view instanceof WebView) {
            ((WebView) view).removeAllViews();
            ((WebView) view).destroy();
        }
    }

//    public static String getKeyHash(final Context context) {
//        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
//        if (packageInfo == null)
//            return null;
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
//            } catch (NoSuchAlgorithmException e) {
//                Log.w("Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//        return null;
//    }

    @SuppressLint("MissingPermission")
    public static String getDevicesUUID(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(mContext.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        return deviceId;
    }


}
