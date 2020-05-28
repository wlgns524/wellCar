package com.rightcode.wellcar.network;

import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.auth.Join;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.model.request.auth.PasswordChange;
import com.rightcode.wellcar.network.model.request.chat.ChatRegister;
import com.rightcode.wellcar.network.model.request.estimate.EstimateRegister;
import com.rightcode.wellcar.network.model.request.estimateStore.EstimateStoreUpdate;
import com.rightcode.wellcar.network.model.request.inquiry.InquiryRegister;
import com.rightcode.wellcar.network.model.request.payment.BuyCheck;
import com.rightcode.wellcar.network.model.request.payment.PaymentEstimateBuyInfo;
import com.rightcode.wellcar.network.model.request.payment.PaymentTicketBuyInfo;
import com.rightcode.wellcar.network.model.request.storeReview.StoreReviewRegister;
import com.rightcode.wellcar.network.model.request.ticketHistory.TicketHistoryRegister;
import com.rightcode.wellcar.network.model.request.user.UserStoreUpdate;
import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.responser.accountCompany.SettlementListResponser;
import com.rightcode.wellcar.network.responser.auth.FindLoginIdResponser;
import com.rightcode.wellcar.network.responser.auth.LoginResponser;
import com.rightcode.wellcar.network.responser.brand.BrandListResponser;
import com.rightcode.wellcar.network.responser.car.CarListResponser;
import com.rightcode.wellcar.network.responser.chatRoom.ChatRoomDetailResponser;
import com.rightcode.wellcar.network.responser.chatRoom.ChatRoomListResponser;
import com.rightcode.wellcar.network.responser.estimate.EstimateDetailResponser;
import com.rightcode.wellcar.network.responser.estimate.EstimateListResponser;
import com.rightcode.wellcar.network.responser.estimate.EstimateStoreListResponser;
import com.rightcode.wellcar.network.responser.event.EventDetailResponser;
import com.rightcode.wellcar.network.responser.event.EventListResponser;
import com.rightcode.wellcar.network.responser.item.ItemListResponser;
import com.rightcode.wellcar.network.responser.item.ItemsPriceResponser;
import com.rightcode.wellcar.network.responser.itemBrand.ItemBrandListResponser;
import com.rightcode.wellcar.network.responser.notice.NoticeDetailResponser;
import com.rightcode.wellcar.network.responser.notice.NoticeListResponser;
import com.rightcode.wellcar.network.responser.notification.NotificationDetailResponser;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateBuyInfoResponser;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateDetailResponser;
import com.rightcode.wellcar.network.responser.paymentEstimate.PaymentEstimateListResponser;
import com.rightcode.wellcar.network.responser.paymentTicket.PaymentTicketListResponser;
import com.rightcode.wellcar.network.responser.shoppingMall.ShoppingMallDetailResponser;
import com.rightcode.wellcar.network.responser.shoppingMall.ShoppingMallListResponser;
import com.rightcode.wellcar.network.responser.store.StoreDetailResponser;
import com.rightcode.wellcar.network.responser.store.StoreListResponser;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewDetailResponser;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewListResponser;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewRegisterResponser;
import com.rightcode.wellcar.network.responser.ticketHistory.TicketHistoryListResponser;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;
import com.rightcode.wellcar.network.responser.version.VersionResponser;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    @GET("/visitors")
    Call<CommonResult> visitor(
    );
    //----------------------------------------------------------------------------------------------
    // version
    //----------------------------------------------------------------------------------------------

    @GET("/version")
    Call<VersionResponser> version(
    );

    //----------------------------------------------------------------------------------------------
    // auth
    //----------------------------------------------------------------------------------------------


    @GET("/auth/CertificationNumberSMS")
    Call<CommonResult> certificationNumberSMS(
            @Query("tel") String tel,
            @Query("diff") String diff
    );

    @GET("/auth/confirm")
    Call<CommonResult> confirm(
            @Query("tel") String tel,
            @Query("confirm") String confirm
    );

    @POST("/auth/join")
    Call<CommonResult> join(
            @Body Join param
    );

    @POST("/auth/login")
    Call<LoginResponser> login(
            @Body Login param
    );

    @GET("/auth/findLoginId")
    Call<FindLoginIdResponser> findLoginId(
            @Query("tel") String tel
    );

    @GET("/auth/existLoginId")
    Call<CommonResult> existLoginId(
            @Query("loginId") String loginId
    );

    @POST("/auth/passwordChange")
    Call<CommonResult> passwordChange(
            @Body PasswordChange param
    );

    //----------------------------------------------------------------------------------------------
    // user
    //----------------------------------------------------------------------------------------------

    @GET("/user/info")
    Call<UserInfoResponser> userInfo(
    );

    @PUT("/user/update")
    Call<UserInfoResponser> userUpdate(
            @Body UserUpdate param
    );

    @GET("/user/logout")
    Call<CommonResult> logout(
    );

    @DELETE("/user/withdrawal")
    Call<CommonResult> userDrop(
    );

    @PUT("/user/store/update")
    Call<CommonResult> userStoreUpdate(
            @Body UserStoreUpdate param
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


    //----------------------------------------------------------------------------------------------
    // item
    //----------------------------------------------------------------------------------------------

    @GET("/item/list")
    Call<ItemListResponser> itemList(
            @Query("diff") String diff,
            @Query("itemBrandId") Integer itemBrandId,
            @Query("storeId") Integer storeId

    );

    @GET("/item/price")
    Call<ItemsPriceResponser> itemPrice(
            @Query("id") Integer id
    );


    //----------------------------------------------------------------------------------------------
    // itemBrand
    //----------------------------------------------------------------------------------------------

    @GET("/itemBrand/list")
    Call<ItemBrandListResponser> itemBrandList(
            @Query("diff") String diff,
            @Query("random") Boolean random,
            @Query("storeId") Integer storeId
    );


    //----------------------------------------------------------------------------------------------
    // store
    //----------------------------------------------------------------------------------------------

    @GET("/store/list")
    Call<StoreListResponser> storeList(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("si") String si,
            @Query("gu") String gu,
            @Query("itemId") ArrayList<Integer> itemId,
            @Query("itemBrandId") Integer itemBrandId,
            @Query("diff") String... diff);



    @GET("/store/detail")
    Call<StoreDetailResponser> storeDetail(
            @Query("id") Integer id
    );

    @Multipart
    @POST("/store/file/register")
    Call<CommonResult> storeThumbnailRegister(
            @Query("diff") String diff,
            @Query("storeId") Integer storeId,
            @Part MultipartBody.Part... image
    );


    //----------------------------------------------------------------------------------------------
    // estimate
    //----------------------------------------------------------------------------------------------

    @POST("/estimate/register")
    Call<CommonResult> estimateRegister(
            @Body EstimateRegister param
    );

    @GET("/estimate/list")
    Call<EstimateListResponser> estimateList(
    );


    @GET("/estimate/detail")
    Call<EstimateDetailResponser> estimateDetail(
            @Query("id") Integer id
    );


    @DELETE("/estimate/remove")
    Call<CommonResult> estimateRemove(
            @Query("id") Integer id
    );

    //----------------------------------------------------------------------------------------------
    // estimateStore
    //----------------------------------------------------------------------------------------------

    @GET("v2/estimateStore/list")
    Call<EstimateStoreListResponser> estimateStoreList(
    );

    @PUT("/estimateStore/update")
    Call<CommonResult> estimateStoreUpdate(
            @Query("id") Integer id,
            @Body EstimateStoreUpdate param

    );


    //----------------------------------------------------------------------------------------------
    // brand
    //----------------------------------------------------------------------------------------------

    @GET("/brand/list")
    Call<BrandListResponser> brandList(
            @Query("origin") String origin
    );


    //----------------------------------------------------------------------------------------------
    // car
    //----------------------------------------------------------------------------------------------

    @GET("/car/list")
    Call<CarListResponser> carList(
            @Query("brandId") Integer brandId,
            @Query("name") String name
    );

    //----------------------------------------------------------------------------------------------
    // notice
    //----------------------------------------------------------------------------------------------

    @GET("/notice/list")
    Call<NoticeListResponser> noticeList(
    );


    @GET("/notice/detail")
    Call<NoticeDetailResponser> noticeDetail(
            @Query("id") Integer id
    );

    //----------------------------------------------------------------------------------------------
    // inquiry
    //----------------------------------------------------------------------------------------------

    @POST("/inquiry/register")
    Call<CommonResult> inquiryRegister(
            @Body InquiryRegister param
    );

    //----------------------------------------------------------------------------------------------
    // event
    //----------------------------------------------------------------------------------------------

    @GET("/event/list")
    Call<EventListResponser> eventList(
            @Query("latitude") Double latitude,
            @Query("longitude") Double longitude,
            @Query("location") String location
    );

    @GET("/event/detail")
    Call<EventDetailResponser> eventDetail(
            @Query("id") Integer id
    );

    //----------------------------------------------------------------------------------------------
    // storeReview
    //----------------------------------------------------------------------------------------------


    @GET("/storeReview/check")
    Call<CommonResult> storeReviewCheck(
            @Query("storeId") Integer storeId
    );

    @GET("/storeReview/list")
    Call<StoreReviewListResponser> storeReviewList(
            @Query("storeId") Integer storeId,
            @Query("realTime") Boolean realTime,
            @Query("search") String search
    );


    @GET("/storeReview/detail")
    Call<StoreReviewDetailResponser> storeReviewDetail(
            @Query("id") Integer id
    );

    @POST("/storeReview/register")
    Call<StoreReviewRegisterResponser> storeReviewRegister(
            @Query("storeId") Integer storeId,
            @Body StoreReviewRegister param
    );

    @DELETE("/storeReview/remove")
    Call<CommonResult> storeReviewRemove(
            @Query("id") Integer id
    );


    //----------------------------------------------------------------------------------------------
    // storeReviewImage
    //----------------------------------------------------------------------------------------------

    @Multipart
    @POST("/storeReviewImage/register")
    Call<CommonResult> storeReviewImageRegister(
            @Query("storeReviewId") Integer storeReviewId,
            @Part MultipartBody.Part... image
    );

    //----------------------------------------------------------------------------------------------
    // storeReviewUser
    //----------------------------------------------------------------------------------------------


    @GET("/storeReview/user/list")
    Call<StoreReviewListResponser> storeReviewUserList(
    );

    //----------------------------------------------------------------------------------------------
    // chat
    //----------------------------------------------------------------------------------------------

    @POST("/chat/register")
    Call<CommonResult> chatRegister(
            @Query("chatRoomId") Integer chatRoomId,
            @Body ChatRegister param
    );

    //----------------------------------------------------------------------------------------------
    // chatRoom
    //----------------------------------------------------------------------------------------------

    @GET("/chatRoom/list")
    Call<ChatRoomListResponser> chatRoomList(
    );

    @GET("/chatRoom/detail")
    Call<ChatRoomDetailResponser> chatRoomDetail(
            @Query("id") Integer id
    );

    @DELETE("/chatRoom/remove")
    Call<StoreReviewListResponser> chatRoomRemove(
            @Query("id") Integer id
    );

    //----------------------------------------------------------------------------------------------
    // payment/estimate
    //----------------------------------------------------------------------------------------------

    @POST("/payment/estimate/buyInfo")
    Call<PaymentEstimateBuyInfoResponser> paymentEstimateBuyInfo(
            @Body PaymentEstimateBuyInfo param
    );

    @POST("/payment/buyCheck")
    Call<CommonResult> paymentBuyCheck(
            @Body BuyCheck param
    );

    @GET("/payment/estimate/list")
    Call<PaymentEstimateListResponser> paymentEstimateList(
    );

    @GET("/payment/estimate/detail")
    Call<PaymentEstimateDetailResponser> paymentEstimateDetail(
            @Query("id") Integer paymentId
    );


    //----------------------------------------------------------------------------------------------
    // payment/ticket
    //----------------------------------------------------------------------------------------------
    @POST("/payment/ticket/buyInfo")
    Call<PaymentEstimateBuyInfoResponser> paymentTicketBuyInfo(
            @Body PaymentTicketBuyInfo param
    );

    @GET("/payment/ticket/list")
    Call<PaymentTicketListResponser> paymentTicketList(
            @Query("date") String date
    );

    //----------------------------------------------------------------------------------------------
    // /shoppingMall
    //----------------------------------------------------------------------------------------------

    @GET("/shoppingMall/list")
    Call<ShoppingMallListResponser> shoppingMallList(
    );

    @GET("/shoppingMall/detail")
    Call<ShoppingMallDetailResponser> shoppingMallDetail(
            @Query("id") Integer shoppingMallId
    );

    //----------------------------------------------------------------------------------------------
    // /ticketHistory
    //----------------------------------------------------------------------------------------------

    @POST("/ticketHistory/register")
    Call<CommonResult> ticketHistoryRegister(
            @Query("storeId") Integer storeId,
            @Body TicketHistoryRegister param
    );

    @GET("/ticketHistory/management")
    Call<TicketHistoryListResponser> ticketHistoryManagement(
    );

    //----------------------------------------------------------------------------------------------
    // /notification
    //----------------------------------------------------------------------------------------------

    @PUT("/notification/update")
    Call<CommonResult> notificationUpdate(
            @Query("notificationToken") String notificationToken,
            @Query("active") Boolean active,
            @Query("chatActive") Boolean chatActive
    );

    @FormUrlEncoded
    @POST("/notification/register")
    Call<CommonResult> notificationRegister(
            @Field("notificationToken") String notificationToken
    );

    @GET("/notification/detail")
    Call<NotificationDetailResponser> notificationDetail(
            @Query("notificationToken") String notificationToken
    );

    //----------------------------------------------------------------------------------------------
    // v1/accounts/company/list
    //----------------------------------------------------------------------------------------------

    @GET("/v1/accounts/company/list")
    Call<SettlementListResponser> carWashSettlementList(
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("diff") String diff
    );

//    eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibG9naW5JZCI6IndsZ25zNTI0Iiwicm9sZSI6IuyXheyytCIsImlhdCI6MTU4NDY4NTk2OSwiZXhwIjo0NzQwNDQ1OTY5LCJpc3MiOiJ3ZWxjYXIifQ.T6qK5zPmT8pELIjfZKHIG6SP_BYKMlQETlfShL1TLZ0
}