package com.rightcode.wellcar.network.requester.storeReviewImage;

import android.content.Context;

import com.rightcode.wellcar.Util.FileUtil;
import com.rightcode.wellcar.network.requester.AbstractRequester;

import java.io.File;
import java.util.ArrayList;

import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class StoreReviewImageRegisterRequester  extends AbstractRequester {

    @Setter
    private Integer storeReviewId;
    @Setter
    private ArrayList<String> path;
    private Context context;

    public StoreReviewImageRegisterRequester(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected Call genApi() {
        ArrayList<MultipartBody.Part> requestBodys = new ArrayList();
        for (String data : path) {

            File file = new File(data);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), FileUtil.rotateImageFileToCache(context, data));

            requestBodys.add(MultipartBody.Part.createFormData("image", file.getName(), requestBody));
        }

        MultipartBody.Part[] array = new MultipartBody.Part[requestBodys.size()];
        int size = 0;
        for (MultipartBody.Part temp : requestBodys) {
            array[size++] = temp;
        }
        Call call = networkManager.getApi().storeReviewImageRegister(storeReviewId, array);

        return call;
    }
}
