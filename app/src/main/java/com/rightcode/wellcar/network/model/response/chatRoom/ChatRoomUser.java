package com.rightcode.wellcar.network.model.response.chatRoom;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomUser {

    @JsonField
    Integer id;
    @JsonField
    ChatRoomGeneral general;
    @JsonField
    ChatRoomCar car;
}
