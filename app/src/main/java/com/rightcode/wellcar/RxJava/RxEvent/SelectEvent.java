package com.rightcode.wellcar.RxJava.RxEvent;

import com.rightcode.wellcar.network.model.response.user.UserInfo;

import lombok.Getter;

@Getter
public class SelectEvent {

    Integer position;
    Boolean isSelect;

    public SelectEvent(Integer position, Boolean isSelect) {
        this.position = position;
        this.isSelect = isSelect;
    }
}
