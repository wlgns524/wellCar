package com.rightcode.wellcar.network;

import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.responser.userInfo.UserInfoResponser;
import com.rightcode.wellcar.network.responser.version.VersionResponser;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LegacyNetworkApi {

    //----------------------------------------------------------------------------------------------
    // visitor
    //----------------------------------------------------------------------------------------------

    @GET("/visitor")
    Call<CommonResult> visitor(
    );
    //----------------------------------------------------------------------------------------------
    // version
    //----------------------------------------------------------------------------------------------

    @GET("/version")
    Call<VersionResponser> version(
    );

    //----------------------------------------------------------------------------------------------
    // user
    //----------------------------------------------------------------------------------------------

    @GET("/user/info")
    Call<UserInfoResponser> userInfo(
    );

    @PUT("/user/update")
    Call<CommonResult> userUpdate(
            @Body UserUpdate parameter
    );

    @GET("/user/logout")
    Call<CommonResult> logout(
    );

    @DELETE("/user/withdrwal")
    Call<CommonResult> userDrop(
    );

    //----------------------------------------------------------------------------------------------
    // boardImage
    //----------------------------------------------------------------------------------------------

    @Multipart
    @POST("/boardImage/register")
    Call<CommonResult> boardImageRegister(
            @Query("boardId") Integer boardId,
            @Part MultipartBody.Part... image
    );

}