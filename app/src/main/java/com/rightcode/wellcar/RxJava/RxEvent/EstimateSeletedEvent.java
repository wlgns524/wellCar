package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class EstimateSeletedEvent {

    private Integer id;

    public EstimateSeletedEvent(Integer id) {
        this.id = id;
    }
}
