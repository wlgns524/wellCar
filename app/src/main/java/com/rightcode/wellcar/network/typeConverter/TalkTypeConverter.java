package com.rightcode.wellcar.network.typeConverter;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.wellcar.Util.DataEnums;

public class TalkTypeConverter extends StringBasedTypeConverter<DataEnums.TalkDiffType> {

    @Override
    public DataEnums.TalkDiffType getFromString(String s) {
        return DataEnums.TalkDiffType.getEnum(s);
    }

    public String convertToString(DataEnums.TalkDiffType object) {
        return object == null ? "" : object.toString();
    }
}
