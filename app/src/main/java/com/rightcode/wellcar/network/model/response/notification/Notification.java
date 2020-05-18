package com.rightcode.wellcar.network.model.response.notification;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Notification {

    @JsonField
    String notificationToken;
    @JsonField
    Boolean active;
    @JsonField
    Boolean chatActive;

}
