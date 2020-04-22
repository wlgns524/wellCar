package com.rightcode.wellcar.network.model.response.chatRoom;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.item.Item;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoom {

    @JsonField
    Integer id;
    @JsonField
    String createdAt;
    @JsonField
    ChatRoomEstimateStore estimateStore;
    @JsonField
    ArrayList<String> chats;
    @JsonField
    Integer viewCount;
    // 업체용
    @JsonField
    ChatRoomStore store;
    // 유저용
    @JsonField
    ChatRoomUser user;

}
