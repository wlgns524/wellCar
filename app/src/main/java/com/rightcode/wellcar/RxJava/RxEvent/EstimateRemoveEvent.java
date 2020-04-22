package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class EstimateRemoveEvent {

    private Integer id;

    public EstimateRemoveEvent(Integer id) {
        this.id = id;
    }
}
