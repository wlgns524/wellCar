package com.rightcode.wellcar.Fragment.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rd.PageIndicatorView;
import com.rightcode.wellcar.Activity.AroundActivity;
import com.rightcode.wellcar.Activity.CarRegist.CarRegisterActivity;
import com.rightcode.wellcar.Activity.Estimate.EstimateDepth1Activity;
import com.rightcode.wellcar.Activity.EventListActivity;
import com.rightcode.wellcar.Activity.Login.LoginActivity;
import com.rightcode.wellcar.Activity.ReviewActivity;
import com.rightcode.wellcar.Activity.Setting.CarWashActivity;
import com.rightcode.wellcar.Activity.ShoppingMallActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.HomeReviewRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.ViewPagerAdapter.HomeBannerViewPagerAdapter;
import com.rightcode.wellcar.Component.CustomViewPager;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.request.auth.Login;
import com.rightcode.wellcar.network.model.response.car.Car;
import com.rightcode.wellcar.network.model.response.user.UserInfo;
import com.rightcode.wellcar.network.requester.event.EventListRequester;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewListRequester;
import com.rightcode.wellcar.network.responser.event.EventListResponser;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewListResponser;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Activity.AroundActivity.around_timer;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.layout_unregist_car)
    View layout_unregist_car;
    // layout_regist_car
    @BindView(R.id.layout_regist_car)
    View layout_regist_car;
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
    //Home Fragment
    @BindView(R.id.cv_banner)
    CustomViewPager cv_banner;
    @BindView(R.id.pageindicator)
    PageIndicatorView pageindicator;
    @BindView(R.id.rv_home_review)
    RecyclerView rv_home_review;

    private TopFragment mTopFragment;
    private HomeBannerViewPagerAdapter mHomeBannerViewPagerAdapter;
    private HomeReviewRecyclerViewAdapter mHomeReviewRecyclerViewAdapter;
    private View root;
    private int currentPage;
    private Timer timer;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------
    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();
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
        root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        initialize();
        eventList();
        storeReview();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initLayoutUserInfo();

        autoScrollViewPager();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pageindicator.setSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @OnClick({R.id.tv_change_car, R.id.layout_regist_car,
            R.id.rl_estimate, R.id.rl_shopping_mall, R.id.rl_car_wash, R.id.rl_around, R.id.rl_review, R.id.rl_event,
            R.id.iv_home_banner, R.id.layout_unregist_car})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_change_car:
            case R.id.layout_regist_car: {
                Intent intent = new Intent(getContext(), CarRegisterActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_estimate: {
                if (MemberManager.getInstance(getContext()).isLogin()) {
                    Intent intent = new Intent(getContext(), EstimateDepth1Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.rl_shopping_mall: {
                Intent intent = new Intent(getContext(), ShoppingMallActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_car_wash: {
                if(MemberManager.getInstance(getContext()).isLogin()){
                    Intent intent = new Intent(getContext(), CarWashActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.rl_around: {
                Intent intent = new Intent(getContext(), AroundActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_review: {
                Intent intent = new Intent(getContext(), ReviewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.rl_event: {
                Intent intent = new Intent(getContext(), EventListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.iv_home_banner: {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCjLmQQtjTTsKNP-egBAbmrQ"));
                startActivity(intent);
                break;
            }
            case R.id.layout_unregist_car: {
                Intent intent = null;
                if (MemberManager.getInstance(getContext()).isLogin()) {
                    intent = new Intent(getContext(), CarRegisterActivity.class);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                }
                if (intent != null)
                    startActivity(intent);
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        mHomeBannerViewPagerAdapter = new HomeBannerViewPagerAdapter(getChildFragmentManager(), getContext());
        cv_banner.setAdapter(mHomeBannerViewPagerAdapter);
        cv_banner.addOnPageChangeListener(this);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_home_review.setLayoutManager(verticalLayoutManager);
        mHomeReviewRecyclerViewAdapter = new HomeReviewRecyclerViewAdapter(getContext());
        rv_home_review.setAdapter(mHomeReviewRecyclerViewAdapter);
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.title_bar_logo);
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
//        mTopFragment.setImagePadding(TopFragment.Menu.RIGHT, 5);
//        mTopFragment.setImage(TopFragment.Menu.RIGHT, R.drawable.ic_bell);
//        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);


    }

    private void initLayoutUserInfo() {
        MemberManager memberManager = MemberManager.getInstance(getContext());
        if (memberManager.isLogin()) {
            layout_regist_car.setVisibility(View.VISIBLE);
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
                Log.d(car.getBrand());
                Glide.with(getContext())
                        .load(car.getImage() != null ? car.getImage().getName() : "")
                        .centerCrop()
                        .override(36, 36)
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv_car_logo);
                tv_car_brand_name.setText(car.getName());
                tv_car_year.setText(car.getModelYear());
            }
        } else {
            layout_regist_car.setVisibility(View.GONE);
            layout_unregist_car.setVisibility(View.VISIBLE);
        }
    }

    private void eventList() {
        showLoading();
        EventListRequester eventListRequester = new EventListRequester(getContext());
        eventListRequester.setLocation("top");

        request(eventListRequester,
                success -> {
                    EventListResponser result = (EventListResponser) success;
                    if (result.getCode() == 200) {
//                        pageindicator.setCount(result.getList().size());
                        mHomeBannerViewPagerAdapter.setData(result.getList());
                        mHomeBannerViewPagerAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void storeReview() {
        showLoading();
        StoreReviewListRequester storeReviewListRequester = new StoreReviewListRequester(getContext());
        storeReviewListRequester.setRealTime(true);

        request(storeReviewListRequester,
                success -> {
                    StoreReviewListResponser result = (StoreReviewListResponser) success;
                    if (result.getCode() == 200) {
                        mHomeReviewRecyclerViewAdapter.setData(result.getList());
                        mHomeReviewRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void autoScrollViewPager() {
        Handler handler = new Handler();
        currentPage = cv_banner.getCurrentItem();
        Runnable Update = new Runnable() {
            @Override
            public void run() {
                cv_banner.setCurrentItem(currentPage,true);
                currentPage += 1;

                if(currentPage >= mHomeBannerViewPagerAdapter.getCount()){
                    currentPage = 0;
                }
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);
    }

}
