package com.rightcode.wellcar.network.error;


import com.rightcode.wellcar.R;

public enum NetworkErrorCode {
    /**
     * ordinal을 에러코드로 사용함으로, 순서 변경 시 고려해야 한다.
     */
    SOCKET_TIME_OUT(R.string.server_connect_error_message),
    UNKNOWN_HOST(R.string.server_connect_error_message),
    IO(R.string.server_connect_error_message),
    ILLEGAL_ARGUMENT(R.string.server_connect_error_message),
    EXCEPTION(R.string.server_connect_error_message),
    IO_NOT_ACTIVE_NETWORK(R.string.network_error_message),
    IO_NETWORK_DISCONNECTED(R.string.network_error_message),

    MAKE_REQUEST_DATA_THROWABLE(R.string.server_connect_error_message),
    REQUEST_THROWABLE(R.string.server_connect_error_message);

    private int messageResId;

    public int getMessageResId() {
        return messageResId;
    }

    NetworkErrorCode(int messageResId) {
        this.messageResId = messageResId;
    }

    @Override
    public String toString(){
        return String.valueOf(this.ordinal());
    }
}

