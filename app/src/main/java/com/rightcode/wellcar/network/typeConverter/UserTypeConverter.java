package com.rightcode.wellcar.network.typeConverter;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.wellcar.Util.DataEnums;

public class UserTypeConverter extends StringBasedTypeConverter<DataEnums.UserType> {

    @Override
    public DataEnums.UserType getFromString(String s) {
        return DataEnums.UserType.getEnum(s);
    }

    public String convertToString(DataEnums.UserType object) {
        return object == null ? "" : object.toString();
    }
}
