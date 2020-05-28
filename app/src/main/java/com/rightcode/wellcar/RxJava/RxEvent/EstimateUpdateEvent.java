package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class EstimateUpdateEvent {

    private Integer id;
    private Integer price;
    private String content;

    public EstimateUpdateEvent(Integer id, Integer price, String content) {
        this.id = id;
        this.price = price;
        this.content = content;
    }
}
