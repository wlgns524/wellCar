package com.rightcode.wellcar.Activity.CarRegist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.iid.FirebaseInstanceId;
import com.rightcode.wellcar.Activity.BaseActivity;
import com.rightcode.wellcar.Activity.Login.LoginActivity;
import com.rightcode.wellcar.Activity.Login.SignUpActivity;
import com.rightcode.wellcar.Activity.Login.SignUpCompleteActivity;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.request.user.UserUpdate;
import com.rightcode.wellcar.network.requester.notification.NotificationRegisterRequester;
import com.rightcode.wellcar.network.requester.user.UserInfoRequester;
import com.rightcode.wellcar.network.requester.user.UserUpdateRequester;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_COMPLETE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_CAR_REGIST;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_CAR_REGIST_ROOT;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_CHANGE_CAR;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SIGN_UP;

public class CarRegisterActivity extends BaseActivity {

    @BindView(R.id.tv_car_register_brand)
    TextView tv_car_register_brand;
    @BindView(R.id.tv_car_register_vehicle)
    TextView tv_car_register_vehicle;
    @BindView(R.id.tv_car_register_year)
    TextView tv_car_register_year;

    private TopFragment mTopFragment;
    private String brand;
    private Integer brandId;
    private String vehicle;
    private String year;
    private Integer carId;
    private Integer rootType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_register);

        ButterKnife.bind(this);
        initialize();
    }

    //------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_COMPLETE: {
                    setResult(RESULT_OK);
                    finishWithAnim();
                    break;
                }
                case EXTRA_ACTIVITY_ACTION: {
                    if (TextUtils.isEmpty(data.getStringExtra("brand"))) {
                        tv_car_register_brand.setText(brand);
                    } else {
                        brand = data.getStringExtra("brand");
                        brandId = data.getIntExtra("brandId", 0);
                        tv_car_register_brand.setText(brand);
                    }
                    if (TextUtils.isEmpty(data.getStringExtra("vehicle"))) {
                        tv_car_register_vehicle.setText("차량 선택");
                    } else {
                        vehicle = data.getStringExtra("vehicle");
                        tv_car_register_vehicle.setText(vehicle);
                    }
                    if (TextUtils.isEmpty(data.getStringExtra("year"))) {
                        tv_car_register_year.setText("연식 선택");
                    } else {
                        year = data.getStringExtra("year");
                        tv_car_register_year.setText(year);
                    }
                    carId = data.getIntExtra("carId", 0);
                    break;
                }
                default:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(rootType == EXTRA_SIGN_UP) {
            ToastUtil.show(getApplicationContext(), "뒤로 갈 수 없습니다.");
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.rl_car_register_brand, R.id.rl_car_register_vehicle, R.id.rl_car_register_year, R.id.tv_car_register})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.rl_car_register_brand: {
                Intent intent = new Intent(CarRegisterActivity.this, CarRegistBrandActivity.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.rl_car_register_vehicle: {
                if (TextUtils.isEmpty(brand)) {
                    ToastUtil.show(CarRegisterActivity.this, "차량 브랜드를 선택해주세요");
                    break;
                }
                Intent intent = new Intent(CarRegisterActivity.this, CarRegistVehicleActivity.class);
                intent.putExtra(EXTRA_CAR_REGIST, brandId);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.rl_car_register_year: {
                if (TextUtils.isEmpty(vehicle)) {
                    ToastUtil.show(CarRegisterActivity.this, "차량 종류를 선택해주세요");
                    break;
                }
                Intent intent = new Intent(CarRegisterActivity.this, CarRegistYearActivity.class);
                intent.putExtra(EXTRA_CAR_REGIST, vehicle);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.tv_car_register: {
                if (TextUtils.isEmpty(brand)) {
                    ToastUtil.show(CarRegisterActivity.this, "차량 브랜드를 선택해주세요");
                    break;
                }
                if (TextUtils.isEmpty(vehicle)) {
                    ToastUtil.show(CarRegisterActivity.this, "차량 종류를 선택해주세요");
                    break;
                }

                if (TextUtils.isEmpty(year)) {
                    ToastUtil.show(CarRegisterActivity.this, "차량 연식을 선택해주세요");
                    break;
                }

                userUpdate();
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        if(getIntent() != null){
            rootType = getIntent().getIntExtra(EXTRA_CAR_REGIST_ROOT, EXTRA_CHANGE_CAR);
        } else {
            rootType = EXTRA_CHANGE_CAR;
        }

        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "차량 등록하기");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
//        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
//        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
//        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private void userUpdate() {
        showLoading();
        UserUpdateRequester userUpdateRequester = new UserUpdateRequester(CarRegisterActivity.this);
        UserUpdate param = new UserUpdate();
        param.setCarId(carId);

        userUpdateRequester.setParam(param);
        request(userUpdateRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        if(rootType == EXTRA_SIGN_UP) {
                            userInfo();
                        } else {
                            MemberManager.getInstance(CarRegisterActivity.this).updateLogInInfo(result.getUser());
                            ToastUtil.show(CarRegisterActivity.this, "등록되었습니다");
                            finishWithAnim();
                        }
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void userInfo(){
        UserInfoRequester userInfoRequester = new UserInfoRequester(CarRegisterActivity.this);

        request(userInfoRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(CarRegisterActivity.this).updateLogInInfo(result.getUser());
                        notificationRegister();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void notificationRegister() {
        NotificationRegisterRequester notificationRegisterRequester = new NotificationRegisterRequester(CarRegisterActivity.this);
        notificationRegisterRequester.setNotificationToken(FirebaseInstanceId.getInstance().getToken());

        request(notificationRegisterRequester,
                success -> {

                }, fail -> {

                });
//        setResult(RESULT_OK);
//        finishWithAnim();
        Intent intent = new Intent(CarRegisterActivity.this, SignUpCompleteActivity.class);
        startActivityForResult(intent, EXTRA_ACTIVITY_COMPLETE);
    }
}
