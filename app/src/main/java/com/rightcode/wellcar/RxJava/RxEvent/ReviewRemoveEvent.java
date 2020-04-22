package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class ReviewRemoveEvent {

    private Integer id;

    public ReviewRemoveEvent(Integer id) {
        this.id = id;
    }
}
