package com.rightcode.wellcar.Util;


import com.rightcode.wellcar.Features;

public class Log {
    private static final String TAG = "rightCode_Tag";

    public static void v() {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.v(TAG, buildMessage(""));
        }
    }

    public static void d() {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.d(TAG, buildMessage(""));
        }
    }

    public static void i() {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.i(TAG, buildMessage(""));
        }
    }

    public static void w() {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.w(TAG, buildMessage(""));
        }
    }

    public static void e() {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.e(TAG, buildMessage(""));
        }
    }

    public static void v(Object message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.v(TAG, buildMessage(null, message));
        }
    }

    public static void d(Object message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.d(TAG, buildMessage(null, message));
        }
    }

    public static void i(Object message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.i(TAG, buildMessage(null, message));
        }
    }

    public static void w(Object message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.w(TAG, buildMessage(null, message));
        }
    }


    public static void e(Object message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.e(TAG, buildMessage(null, message));
        }
    }


    /**
     * Fabric Crashlytics의 Non-Fatal 항목으로 로깅된다.
     *
     * @param throwable
     */
    public static void e(Throwable throwable) {
        if (throwable != null) {
            if (Features.TEST_ONLY && Features.SHOW_LOG) {
                android.util.Log.e(TAG, buildMessage(null, throwable.getMessage()));
                throwable.printStackTrace();
            }
        }
    }

    public static void v(String format, Object... message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.v(TAG, buildMessage(format, message));
        }
    }

    public static void d(String format, Object... message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.d(TAG, buildMessage(format, message));
        }
    }

    public static void i(String format, Object... message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.i(TAG, buildMessage(format, message));
        }
    }

    public static void w(String format, Object... message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.w(TAG, buildMessage(format, message));
        }
    }


    public static void e(String format, Object... message) {
        if (Features.TEST_ONLY && Features.SHOW_LOG) {
            android.util.Log.e(TAG, buildMessage(format, message));
        }
    }


    private static String buildMessage(String format, Object... message) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(stackTraceElement.getFileName());
        sb.append(" ");
        sb.append(stackTraceElement.getLineNumber());

        if (format == null || format.isEmpty()) {
            if (message == null || message.length <= 0) {
                sb.append("] >> ");
                sb.append(stackTraceElement.getMethodName());
            } else if (message.length > 1) {
                sb.append("] ");
                sb.append(message);
            } else {
                sb.append("] ");
                sb.append(message[0]);
            }
        } else if (message == null || message.length <= 0) {
            sb.append(format);
        } else {
            sb.append("] ");
            sb.append(String.format(format, message));
        }

        return sb.toString();
    }
}