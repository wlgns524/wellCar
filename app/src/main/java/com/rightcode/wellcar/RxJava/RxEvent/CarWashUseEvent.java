package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class CarWashUseEvent {

    private String code;

    public CarWashUseEvent(String code) {
        this.code = code;
    }
}
