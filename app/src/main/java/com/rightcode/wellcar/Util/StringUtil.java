package com.rightcode.wellcar.Util;

import android.graphics.Paint;
import android.widget.TextView;

public class StringUtil {

    public static void  cancelLine(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
