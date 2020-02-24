package com.rightcode.wellcar.network.error;

import lombok.Getter;

public enum ServerResultCode {
    UNKNOWN(10000),
    SERVER_ERROR(4000),
    REFRESH_TOKEN(0401),
    CHECKING_SERVICE(10001),
    SUCCESS(0000),
//    NEED_TO_LOGIN("0006"),
    DUPLICATIOM(0007),
    NEED_TO_UPDATE(9998);


    @Getter
    private Integer resultCode;

    ServerResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public static ServerResultCode getEnum(String value) {
        for (ServerResultCode resultCode : values()) {
            if (resultCode.toString().equalsIgnoreCase(value)) {
                return resultCode;
            }
        }
        return UNKNOWN;
    }
}
