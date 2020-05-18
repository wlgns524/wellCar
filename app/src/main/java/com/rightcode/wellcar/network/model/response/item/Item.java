package com.rightcode.wellcar.network.model.response.item;


import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.network.model.response.itemBrand.ItemBrand;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class Item implements Parcelable {

    @JsonField
    int id;
    @JsonField
    String name;
    @JsonField
    String diff;
    @JsonField
    ItemBrand itemBrand;

    public Item() {

    }

    public Item(String name) {
        this.name = name;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        diff = in.readString();
        itemBrand = in.readParcelable(ItemBrand.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public DataEnums.ItemDiffType getDiff() {
        switch (diff) {
            case "썬팅":
                return DataEnums.ItemDiffType.SUNBLOCK;
            case "유리막":
                return DataEnums.ItemDiffType.GLASS;
            case "블랙박스":
                return DataEnums.ItemDiffType.BLACKBOX;
            case "언더코팅":
                return DataEnums.ItemDiffType.UNDERCOATING;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(diff);
        dest.writeParcelable(itemBrand, flags);
    }
}
