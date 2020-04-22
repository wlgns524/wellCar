package com.rightcode.wellcar.Util;

import com.bluelinelabs.logansquare.LoganSquare;
import com.rightcode.wellcar.network.typeConverter.ItemDiffTypeConverter;
import com.rightcode.wellcar.network.typeConverter.StoreMenuStatusConverter;
import com.rightcode.wellcar.network.typeConverter.TalkTypeConverter;
import com.rightcode.wellcar.network.typeConverter.UserTypeConverter;

public class DataEnums {

    public void registerTypeConverter() {
        LoganSquare.registerTypeConverter(UserType.class, new UserTypeConverter());
        LoganSquare.registerTypeConverter(StoreMenuStatus.class, new StoreMenuStatusConverter());
        LoganSquare.registerTypeConverter(ItemDiffType.class, new ItemDiffTypeConverter());
        LoganSquare.registerTypeConverter(TalkDiffType.class, new TalkTypeConverter());
    }

    public enum UserType {
        CUSTOMER("고객"),
        COMPANY("업체"),
        NULL("");

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

    public enum DiffType {
        JOIN("join"),
        FIND("find"),
        UPDATE("update");

        private String type;

        DiffType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static DiffType getEnum(String value) {
            for (DiffType resultCode : values()) {
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


    public enum TalkDiffType {
        Chatting("chatting"),
        REVIEW("review");

        private String type;

        TalkDiffType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static TalkDiffType getEnum(String value) {
            for (TalkDiffType resultCode : values()) {
                if (resultCode.toString().equalsIgnoreCase(value)) {
                    return resultCode;
                }
            }
            return null;
        }

        public boolean equals(TalkDiffType str) {
            return type != null ? type.equals(str.toString()) : false;
        }
    }

    public enum ItemDiffType {
        SUNBLOCK("썬팅"),
        GLASS("유리막"),
        BLACKBOX("블랙박스"),
        UNDERCOATING("언더코팅"),
        PPF("ppf"),
        TUNING("튜닝"),
        TIRE("타이어"),
        PACKAGE("패키지");

        private String type;

        ItemDiffType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static ItemDiffType getEnum(String value) {
            for (ItemDiffType resultCode : values()) {
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


    public enum BrandType {
        IMPORTED("수입"),
        DOMESTIC("국산");

        private String type;

        BrandType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static BrandType getEnum(String value) {
            for (BrandType resultCode : values()) {
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


    public enum CompanyDetailType {
        BASIC("기본"),
        ESTIMATE("견적"),
        CLEAN("세차장"),
        MANAGEMENT("관리자");

        private String type;

        CompanyDetailType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }

        public static CompanyDetailType getEnum(String value) {
            for (CompanyDetailType resultCode : values()) {
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
