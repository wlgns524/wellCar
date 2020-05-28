package com.rightcode.wellcar.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rightcode.wellcar.Activity.CarRegist.CarRegisterActivity;
import com.rightcode.wellcar.Activity.Login.LoginActivity;
import com.rightcode.wellcar.Activity.Setting.BuyActivity;
import com.rightcode.wellcar.Activity.Setting.CarCleanActivity;
import com.rightcode.wellcar.Activity.Setting.CarWashManagementActivity;
import com.rightcode.wellcar.Activity.Setting.CompanyManagementActivity;
import com.rightcode.wellcar.Activity.Setting.EstimateCustomerActivity;
import com.rightcode.wellcar.Activity.Setting.InquiryActivity;
import com.rightcode.wellcar.Activity.Setting.NoticeActivity;
import com.rightcode.wellcar.Activity.Setting.NotificationActivity;
import com.rightcode.wellcar.Activity.Setting.ReviewManagementActivity;
import com.rightcode.wellcar.Activity.Setting.UserActivity;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.response.car.Car;
import com.rightcode.wellcar.network.model.response.user.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends BaseFragment {

    @BindView(R.id.layout_unregist_car)
    View layout_unregist_car;
    @BindView(R.id.layout_regist_car_user)
    View layout_regist_car_user;
    @BindView(R.id.iv_car_logo)
    ImageView iv_car_logo;
    @BindView(R.id.tv_car_brand_name)
    TextView tv_car_brand_name;
    @BindView(R.id.tv_car_year)
    TextView tv_car_year;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_car_wash_count)
    TextView tv_car_wash_count;
    @BindView(R.id.tv_point)
    TextView tv_point;


    @BindView(R.id.layout_user_basic_view)
    View layout_user_basic_view;
    @BindView(R.id.layout_user_customer_view)
    View layout_user_customer_view;
    @BindView(R.id.layout_user_company_view)
    View layout_user_company_view;
    @BindView(R.id.rl_company_car_wash)
    RelativeLayout rl_company_car_wash;
    @BindView(R.id.rl_company_company)
    RelativeLayout rl_company_company;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

    private View root;
    private TopFragment mTopFragment;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static UserFragment newInstance() {
        UserFragment f = new UserFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }


    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user, container, false);

        ButterKnife.bind(this, root);
        initialize();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initLayoutUserInfo();
    }


    @OnClick({R.id.tv_change_car, R.id.rl_company_user, R.id.rl_customer_user,  R.id.rl_customer_buy, R.id.rl_company_review,
            R.id.rl_customer_review,  R.id.rl_customer_estimate_customer,  R.id.rl_customer_car_clean,
            R.id.rl_company_notice, R.id.rl_basic_notice, R.id.rl_customer_notice, R.id.rl_company_inquiry, R.id.rl_basic_inquiry, R.id.rl_customer_inquiry,
            R.id.rl_company_notification, R.id.rl_basic_notification, R.id.rl_customer_notification, R.id.rl_company_company, R.id.rl_company_car_wash,
            R.id.tv_logout, R.id.layout_unregist_car})
    void onClickMenu(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.tv_change_car:
                intent = new Intent(getContext(), CarRegisterActivity.class);
                break;
            case R.id.rl_company_user:
            case R.id.rl_customer_user: {
                intent = new Intent(getContext(), UserActivity.class);
                break;
            }
            case R.id.rl_customer_buy: {
                intent = new Intent(getContext(), BuyActivity.class);
                break;
            }
            case R.id.rl_company_review:
            case R.id.rl_customer_review: {
                intent = new Intent(getContext(), ReviewManagementActivity.class);
                break;
            }
            case R.id.rl_customer_estimate_customer: {
                intent = new Intent(getContext(), EstimateCustomerActivity.class);
                break;
            }
            case R.id.rl_customer_car_clean: {
                intent = new Intent(getContext(), CarCleanActivity.class);
                break;
            }
            case R.id.rl_company_notice:
            case R.id.rl_basic_notice:
            case R.id.rl_customer_notice: {
                intent = new Intent(getContext(), NoticeActivity.class);
                break;
            }
            case R.id.rl_company_inquiry:
            case R.id.rl_basic_inquiry:
            case R.id.rl_customer_inquiry: {
                intent = new Intent(getContext(), InquiryActivity.class);
                break;
            }
            case R.id.rl_company_notification:
            case R.id.rl_basic_notification:
            case R.id.rl_customer_notification: {
                intent = new Intent(getContext(), NotificationActivity.class);
                break;
            }
            case R.id.rl_company_company: {
                intent = new Intent(getContext(), CompanyManagementActivity.class);
                break;
            }
            case R.id.rl_company_car_wash: {
                intent = new Intent(getContext(), CarWashManagementActivity.class);
                break;
            }
            case R.id.tv_logout: {
                if (MemberManager.getInstance(getContext()).isLogin()) {
                    CommonDialog commonDialog = new CommonDialog(getContext());
                    commonDialog.setMessage("로그아웃 하시겠습니까 ?");
                    commonDialog.setPositiveButton("확인", ok -> {
                        MemberManager.getInstance(getContext()).userLogout();
                        commonDialog.dismiss();
                        onResume();
                    });
                    commonDialog.setNegativeButton("취소", ok -> {
                        commonDialog.dismiss();
                    });
                    commonDialog.show();
                } else {
                    ToastUtil.show(getContext(), "로그인 후 이용해주세요");
                }
                break;
            }
            case R.id.layout_unregist_car: {
                if (MemberManager.getInstance(getContext()).isLogin()) {
                    intent = new Intent(getContext(), CarRegisterActivity.class);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                }
                break;
            }
        }
        if (intent != null)
            startActivity(intent);
    }


    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.title_bar_logo);
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);

    }

    private void initLayoutUserInfo() {
        MemberManager memberManager = MemberManager.getInstance(getContext());
        if (memberManager.isLogin()) {
            layout_regist_car_user.setVisibility(View.VISIBLE);
            tvLogout.setVisibility(View.VISIBLE);
            layout_unregist_car.setVisibility(View.GONE);

            UserInfo userInfo = MemberManager.getInstance(getContext()).getUserInfo();
            if (memberManager.getUserInfo().getRole().equals(DataEnums.UserType.CUSTOMER)) {
                tv_user_name.setText(MemberManager.getInstance(getContext()).getUserInfo().getGeneral().getNickname());
            } else {
                tv_user_name.setText(MemberManager.getInstance(getContext()).getUserInfo().getCompany().getName());
            }
            tv_car_wash_count.setText(String.format("%d개", userInfo.getCarWashTicket()));
            tv_point.setText(userInfo.getPoint().toString());
            if (userInfo.getCar() != null) {
                Car car = userInfo.getCar();
                Glide.with(getContext())
                        .load(car.getImage() != null ? car.getImage().getName() : "")
                        .centerCrop()
                        .override(36, 36)
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv_car_logo);
                tv_car_brand_name.setText(car.getName());
                tv_car_year.setText(car.getModelYear());

            }

            if (userInfo.getRole().equals(DataEnums.UserType.CUSTOMER)) {
                layout_user_basic_view.setVisibility(View.GONE);
                layout_user_customer_view.setVisibility(View.VISIBLE);
                layout_user_company_view.setVisibility(View.GONE);
            } else if (userInfo.getRole().equals(DataEnums.UserType.COMPANY)) {
                layout_user_basic_view.setVisibility(View.GONE);
                layout_user_customer_view.setVisibility(View.GONE);
                layout_user_company_view.setVisibility(View.VISIBLE);
                if (userInfo.getStore() != null) {
                    if (userInfo.getStore().getPosition().equals("튜닝")) {
                        rl_company_company.setVisibility(View.VISIBLE);
                        rl_company_car_wash.setVisibility(View.GONE);
                    } else if (userInfo.getStore().getPosition().equals("세차")) {
                        rl_company_company.setVisibility(View.GONE);
                        rl_company_car_wash.setVisibility(View.VISIBLE);
                    } else {
                        rl_company_company.setVisibility(View.INVISIBLE);
                        rl_company_car_wash.setVisibility(View.GONE);
                    }
                } else {
                    rl_company_company.setVisibility(View.INVISIBLE);
                    rl_company_car_wash.setVisibility(View.GONE);
                }
            } else {
                layout_user_basic_view.setVisibility(View.VISIBLE);
                layout_user_customer_view.setVisibility(View.GONE);
                layout_user_company_view.setVisibility(View.GONE);
            }
        } else {
            layout_regist_car_user.setVisibility(View.GONE);
            layout_unregist_car.setVisibility(View.VISIBLE);
            layout_user_basic_view.setVisibility(View.VISIBLE);
            layout_user_customer_view.setVisibility(View.GONE);
            layout_user_company_view.setVisibility(View.GONE);
            tvLogout.setVisibility(View.GONE);
        }

    }
}
