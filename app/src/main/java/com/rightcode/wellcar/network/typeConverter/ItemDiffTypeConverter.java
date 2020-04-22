package com.rightcode.wellcar.network.typeConverter;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.wellcar.Util.DataEnums;

public class ItemDiffTypeConverter extends StringBasedTypeConverter<DataEnums.ItemDiffType> {

    @Override
    public DataEnums.ItemDiffType getFromString(String s) {
        return DataEnums.ItemDiffType.getEnum(s);
    }

    public String convertToString(DataEnums.ItemDiffType object) {
        return object == null ? "" : object.toString();
    }
}
