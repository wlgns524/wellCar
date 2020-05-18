package com.rightcode.wellcar.network.model.response.user;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class UserStore {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    String address;
    @JsonField
    String addressDetail;
    @JsonField
    String introduction;
    @JsonField
    String opens;
    @JsonField
    String position;
    @JsonField
    Image thumbnail;
}
