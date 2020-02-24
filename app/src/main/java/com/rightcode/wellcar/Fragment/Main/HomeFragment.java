package com.rightcode.wellcar.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.HomeReviewRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.ViewPagerAdapter.HomeBannerViewPagerAdapter;
import com.rightcode.wellcar.Component.CustomViewPager;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.Util.FragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.layout_unregist_car)
    View layout_unregist_car;
    @BindView(R.id.layout_regist_car)
    View layout_regist_car;
    @BindView(R.id.cv_banner)
    CustomViewPager cv_banner;
    @BindView(R.id.pageindicator)
    PageIndicatorView pageindicator;
    @BindView(R.id.rv_home_review)
    RecyclerView rv_home_review;

    private TopFragment mTopFragment;
    private HomeBannerViewPagerAdapter mHomeBannerViewPagerAdapter;
    private HomeReviewRecyclerViewAdapter mHomeReviewRecyclerViewAdapter;

    //----------------------------------------------------------------------------------------------
    // Life Cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, rootView);
        initialize();

        RxBus.register(this);
        return rootView;
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


    @OnClick({R.id.layout_unregist_car, R.id.layout_regist_car})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.layout_unregist_car: {
                layout_unregist_car.setVisibility(View.GONE);
                layout_regist_car.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.layout_regist_car: {
                layout_unregist_car.setVisibility(View.VISIBLE);
                layout_regist_car.setVisibility(View.GONE);
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
        pageindicator.setCount(5);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_home_review.setLayoutManager(verticalLayoutManager);
        mHomeReviewRecyclerViewAdapter = new HomeReviewRecyclerViewAdapter(getContext());
        rv_home_review.setAdapter(mHomeReviewRecyclerViewAdapter);
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
//        mTopFragment.setImage(TopFragment.Menu.RIGHT, R.drawable.search);
//        mTopFragment.setImagePadding(TopFra
//        gment.Menu.RIGHT, 5);
//        mTopFragment.setListener(TopFragment.Menu.RIGHT, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (MemberManager.getInstance(getContext()).isLogin()) {
//                    Intent intent = new Intent(getContext(), SearchActivity.class);
//                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

}
