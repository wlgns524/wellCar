package com.rightcode.wellcar.network.model.response.chatRoom;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.typeConverter.TalkTypeConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@JsonObject
@Data
@Setter
@EqualsAndHashCode(callSuper = false)
public class ChatRoomDetail {

    @JsonField
    String content;
    @JsonField(typeConverter = TalkTypeConverter.class)
    DataEnums.TalkDiffType diff;
    @JsonField
    Boolean isMine;
    @JsonField
    String createdAt;
}
