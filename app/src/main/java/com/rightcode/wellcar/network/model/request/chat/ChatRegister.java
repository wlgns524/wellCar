package com.rightcode.wellcar.network.model.request.chat;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.Util.DataEnums;

import lombok.Setter;

@JsonObject
public class ChatRegister {

    @JsonField
    @Setter
    String content;
    @JsonField
    @Setter
    DataEnums.TalkDiffType diff;
}
