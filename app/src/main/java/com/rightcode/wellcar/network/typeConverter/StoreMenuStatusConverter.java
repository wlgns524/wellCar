package com.rightcode.wellcar.network.typeConverter;

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter;
import com.rightcode.wellcar.Util.DataEnums;

public class StoreMenuStatusConverter extends StringBasedTypeConverter<DataEnums.StoreMenuStatus> {

    @Override
    public DataEnums.StoreMenuStatus getFromString(String s) {
        return DataEnums.StoreMenuStatus.getEnum(s);
    }

    public String convertToString(DataEnums.StoreMenuStatus object) {
        return object == null ? "" : object.toString();
    }
}
