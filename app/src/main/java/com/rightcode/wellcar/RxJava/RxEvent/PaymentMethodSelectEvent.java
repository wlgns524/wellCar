package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PaymentMethodSelectEvent {

    private String method;
    private String type;
    @Setter
    private Integer count;
    @Setter
    private Integer price;

    public PaymentMethodSelectEvent(String method, String type) {
        this.method = method;
        this.type = type;
    }

}
