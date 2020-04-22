package com.rightcode.wellcar;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;

import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.GPSUtil;
import com.rightcode.wellcar.Util.PreferenceUtil;
import com.rightcode.wellcar.network.NetworkBridge;
import com.rightcode.wellcar.network.model.response.user.UserInfo;

import lombok.Getter;
import lombok.Setter;

public class MemberManager {
    //----------------------------------------------------------------------------------------------
    // static final fields
    //----------------------------------------------------------------------------------------------
    private volatile static MemberManager sMemberManager;

    //----------------------------------------------------------------------------------------------
    // fields
    //----------------------------------------------------------------------------------------------
    private Context context;

    @Setter
    @Getter
    private String serviceToken;
    private UserInfo userInfo;
    private NetworkBridge networkBridge;

    //----------------------------------------------------------------------------------------------
    // get / set
    //----------------------------------------------------------------------------------------------
    public boolean isLogin() {
        return !TextUtils.isEmpty(serviceToken);
    }

    //----------------------------------------------------------------------------------------------
    // constructor
    //----------------------------------------------------------------------------------------------
    private MemberManager(Context context) {
        if (context == null) {
            return;
        }
        this.context = context.getApplicationContext();
    }

    public static MemberManager getInstance(Context context) {
        if (sMemberManager == null) {
            synchronized (MemberManager.class) {
                if (sMemberManager == null) {
                    sMemberManager = new MemberManager(context);
                }
            }
        }
        return sMemberManager;
    }

    public void updateLogInInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void userLogin(String userId, String userPw) {
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserId, userId);
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserPw, userPw);
    }


    public void userLogout() {
        serviceToken = null;
        userInfo = null;
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserId, "");
        PreferenceUtil.getInstance(context).put(PreferenceUtil.PreferenceKey.UserPw, "");
    }

//    public DataEnums.UserType getRole() {
//        if (isLogin()) {
//            userInfo.getRole();
//        }
//        return null;
//    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Location getLocation() {
        GPSUtil gpsUtil = new GPSUtil(context);
        return gpsUtil.getLocation();
    }
}
