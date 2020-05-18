package com.rightcode.wellcar.network.model.response.itemBrand;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonObject
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemBrand implements Parcelable {

    @JsonField
    Integer id;
    @JsonField
    String name;
    @JsonField
    String imageName;

    public ItemBrand() {

    }

    public ItemBrand(String name) {
        this.name = name;
    }

    protected ItemBrand(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        imageName = in.readString();
    }

    public static final Creator<ItemBrand> CREATOR = new Creator<ItemBrand>() {
        @Override
        public ItemBrand createFromParcel(Parcel in) {
            return new ItemBrand(in);
        }

        @Override
        public ItemBrand[] newArray(int size) {
            return new ItemBrand[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(imageName);
    }
}
