package com.rightcode.wellcar.network.model.response.brand;


import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.model.response.Image;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Brand {

    @JsonField
    int id;
    @JsonField
    String name;
    @JsonField
    String origin;
    @JsonField
    Image image;

    public DataEnums.BrandType getOrigin() {
        if (origin != null) {
            if (origin.equals("국산")) {
                return DataEnums.BrandType.DOMESTIC;
            } else {
                return DataEnums.BrandType.IMPORTED;
            }
        } else {
            return null;
        }
    }
}
