package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class AroundSelectEvent {

    private String optionTitle;

    public AroundSelectEvent(String optionTitle) {
        this.optionTitle = optionTitle;
    }
}
