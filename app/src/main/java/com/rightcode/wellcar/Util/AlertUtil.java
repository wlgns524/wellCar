package com.rightcode.wellcar.Util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.rightcode.wellcar.Dialog.BaseDialog;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.R;

import rx.functions.Action1;

public class AlertUtil {

    public static BaseDialog show(Context context, int textResourceId) {
        return show(context, context.getString(textResourceId));
    }

    public static BaseDialog show(Context context, String message) {
        return show(context, null, message, null);
    }

    public static BaseDialog show(Context context, String message, Action1<Void> action) {
        return show(context, null, message, action);
    }

    public static BaseDialog show(Context context, int textResourceId, Action1<Void> action) {
        return show(context, null, context.getString(textResourceId), action);
    }

    public static BaseDialog show(Context context, String title, String message) {
        return show(context, title, message, null);
    }

    public static BaseDialog show(Context context, int titleResourceId, int textResourceId) {
        return show(context, context.getString(titleResourceId), context.getString(textResourceId));
    }

    public static BaseDialog show(Context context, int titleResourceId, int textResourceId, Action1<Void> action) {
        return show(context, context.getString(titleResourceId), context.getString(textResourceId), action);
    }

    public static BaseDialog show(Context context, String title, String message, Action1<Void> action) {
        return show(context, title, message, R.string.ok, action, null);
    }

    public static BaseDialog show(Context context, String title, String message, Action1<Void> action, DialogInterface.OnCancelListener cancelListener) {
        return show(context, title, message, R.string.ok, action, cancelListener);
    }

    public static BaseDialog show(Context context, String title, String message, int positiveResId, Action1<Void> action, DialogInterface.OnCancelListener cancelListener) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setMessage(message);
        dialog.setPositiveButton(context.getString(positiveResId), action);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        if (cancelListener != null) {
            dialog.setOnCancelListener(cancelListener);
        }
        dialog.show();

        return dialog;
    }

    public static BaseDialog show(Context context, String message, Action1<Void> no, Action1<Void> yes) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setMessage(message);
        dialog.setPositiveButton(context.getString(R.string.ok), yes);
        dialog.setNegativeButton(context.getString(R.string.cancel), no);
        dialog.show();

        return dialog;
    }

    public static BaseDialog show(Context context, int titleResourceId, int textResourceId, int negativeResId, int positiveResId, Action1<Void> no, Action1<Void> yes) {
        CommonDialog dialog = new CommonDialog(context);
        if (titleResourceId > 0) {
            dialog.setTitle(context.getString(titleResourceId));
        }
        dialog.setMessage(context.getString(textResourceId));
        dialog.setNegativeButton(context.getString(negativeResId), no);
        dialog.setPositiveButton(context.getString(positiveResId), yes);
        dialog.show();

        return dialog;
    }

    public static BaseDialog show(Context context, String title, String message, int negativeResId, int positiveResId, Action1<Void> no, Action1<Void> yes) {
        CommonDialog dialog = new CommonDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        }
        dialog.setMessage(message);
        dialog.setNegativeButton(context.getString(negativeResId), no);
        dialog.setPositiveButton(context.getString(positiveResId), yes);
        dialog.show();

        return dialog;
    }

    public static BaseDialog show(Context context, int titleResourceId, int textResourceId, int negativeResId, int positiveResId, Action1<Void> no, Action1<Void> yes, DialogInterface.OnCancelListener cancelListener) {
        CommonDialog dialog = new CommonDialog(context);
        if (titleResourceId > 0) {
            dialog.setTitle(context.getString(titleResourceId));
        }
        dialog.setMessage(context.getString(textResourceId));
        dialog.setNegativeButton(context.getString(negativeResId), no);
        dialog.setPositiveButton(context.getString(positiveResId), yes);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();

        return dialog;
    }

    public static BaseDialog show(Context context, String text, int negativeResId, int positiveResId, Action1<Void> no, Action1<Void> yes) {
        CommonDialog dialog = new CommonDialog(context);
        dialog.setMessage(text);
        dialog.setNegativeButton(context.getString(negativeResId), no);
        dialog.setPositiveButton(context.getString(positiveResId), yes);
        dialog.show();

        return dialog;
    }
}
