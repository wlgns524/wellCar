package com.rightcode.wellcar.network.model.data;

import lombok.Getter;

public class RecentListData {

    @Getter
    private String imageUrl;
    @Getter
    private Integer storePk;
    @Getter
    private String storeName;
    @Getter
    private String area;

    public RecentListData(String imageUrl, Integer storePk, String storeName, String area) {
        this.imageUrl = imageUrl;
        this.storePk = storePk;
        this.storeName = storeName;
        this.area = area;
    }
}
