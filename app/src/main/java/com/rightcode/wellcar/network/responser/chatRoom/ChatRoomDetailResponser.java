package com.rightcode.wellcar.network.responser.chatRoom;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoom;
import com.rightcode.wellcar.network.model.response.chatRoom.ChatRoomChats;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomDetailResponser extends CommonResult {

    @JsonField
    ChatRoomChats data;
}
