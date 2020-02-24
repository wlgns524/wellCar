package com.rightcode.wellcar.Util;

import android.content.Context;
import android.widget.Toast;

import com.rightcode.wellcar.R;


public class ToastUtil {

    public static Toast show(Context context) {
        return show(context, context.getString(R.string.error_msg));
    }

    public static Toast show(Context context, int textResourceId) {
        return show(context, context.getString(textResourceId));
    }

    public static Toast show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return Toast.makeText(context, message, Toast.LENGTH_SHORT);
    }
}