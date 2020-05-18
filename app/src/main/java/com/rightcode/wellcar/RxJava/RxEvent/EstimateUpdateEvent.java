package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class EstimateUpdateEvent {

    private Integer id;
    private Integer price;

    public EstimateUpdateEvent(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }
}
