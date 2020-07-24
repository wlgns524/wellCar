package com.rightcode.wellcar.RxJava.RxEvent;

import lombok.Getter;

@Getter
public class EstimateCompleteEvent {

    Integer estimateId;
    Integer paymentId;

    public EstimateCompleteEvent(Integer estimateId, Integer paymentId) {
        this.estimateId = estimateId;
        this.paymentId = paymentId;
    }
}
