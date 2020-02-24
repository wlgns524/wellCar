package com.rightcode.wellcar.Util;

import com.bluelinelabs.logansquare.LoganSquare;
import com.rightcode.wellcar.network.typeConverter.StoreMenuStatusConverter;
import com.rightcode.wellcar.network.typeConverter.UserTypeConverter;

public class DataEnums {

    public void registerTypeConverter() {
        LoganSquare.registerTypeConverter(UserType.class, new UserTypeConverter());
        LoganSquare.registerTypeConverter(StoreMenuStatus.class, new StoreMenuStatusConverter());
    }

    public enum UserType {
        MANAGER("담당자"),
        NORMAL("일반");

        private String type;

        UserType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static UserType getEnum(String value) {
            for (UserType resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(UserType code) {
            return type != null ? type.equals(code.toString()) : false;
        }
    }

    public enum CertificationType {
        ID("아이디 찾기"),
        PW("비밀번호 변경");

        private String type;

        CertificationType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static CertificationType getEnum(String value) {
            for (CertificationType resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(UserType code) {
            return type != null ? type.equals(code.toString()) : false;
        }
    }

    public enum TownArea {
        ALL("전체"),
        DONGSONG("동송읍"),
        GALMAL("갈말읍"),
        KIMHWA("김화읍");

        private String type;

        TownArea(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static TownArea getEnum(String value) {
            for (TownArea resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(UserType code) {
            return type != null ? type.equals(code.toString()) : false;
        }
    }

    public enum StoreMenuStatus {

        Avaliable("사용가능"),
        Limit("하루에 1번만 사용 가능합니다"),
        SoldOut("매진");

        private String status;

        StoreMenuStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }

        public static StoreMenuStatus getEnum(String value) {
            for (StoreMenuStatus resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(UserType code) {
            return status != null ? status.equals(code.toString()) : false;
        }
    }
}
