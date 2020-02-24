package com.rightcode.wellcar;

import android.text.TextUtils;

public class ApiEnums {

    public enum Country {
        KR(R.string.phone_code_kr, R.string.korea, "82");

        public int phoneCodeResourceId;
        public int labelResourceId;
        public String number;

        Country(int phoneCodeResourceId, int labelResourceId, String number) {
            this.phoneCodeResourceId = phoneCodeResourceId;
            this.labelResourceId = labelResourceId;
            this.number = number;
        }

        public static Country getEnum(String value) {
            if (TextUtils.isEmpty(value)) {
                return null;
            }

            for (Country resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }

            return null;
        }
        }

    public enum Language {
        ko;

        public static Language getEnum(String value) {
            for (Language resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }
    }
}
