package com.rightcode.wellcar.Adapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.rd.PageIndicatorView;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.AroundHeaderRecyclerViewAdapter;
import com.rightcode.wellcar.Adapter.ViewPagerAdapter.HomeBannerViewPagerAdapter;
import com.rightcode.wellcar.Component.CustomViewPager;
import com.rightcode.wellcar.Component.RecyclerViewOnClickListener;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.RxJava.RxEvent.AroundSelectEvent;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.network.model.response.event.Event;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;


public class AroundHeaderViewHolder extends CommonRecyclerViewHolder implements ViewPager.OnPageChangeListener {

    @BindView(R.id.rv_around_header)
    RecyclerView rv_around_header;
    @BindView(R.id.cv_banner)
    CustomViewPager cv_banner;
    @BindView(R.id.pageindicator)
    PageIndicatorView pageindicator;

    private Context mContext;
    private HomeBannerViewPagerAdapter mHomeBannerViewPagerAdapter;
    private AroundHeaderRecyclerViewAdapter mAroundHeaderRecyclerViewAdapter;

    private ArrayList<String> data = new ArrayList<String>(
            Arrays.asList("썬팅", "블랙박스", "타이어", "유리막", "언더코팅", "튜닝", "셀프세차", "ppf"));
    private ArrayList<Boolean> isSelect = new ArrayList<Boolean>(
            Arrays.asList(false, false, false, false, false, false, false, false));

    public AroundHeaderViewHolder(View viewHolder, Context context) {
        super(viewHolder, context);
        mContext = context;

        ButterKnife.bind(this, itemView);
    }

    public void onBind(FragmentManager fm) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rv_around_header.setLayoutManager(gridLayoutManager);
        mAroundHeaderRecyclerViewAdapter = new AroundHeaderRecyclerViewAdapter(mContext);
        mAroundHeaderRecyclerViewAdapter.setData(data);
        mAroundHeaderRecyclerViewAdapter.setIsSelect(isSelect);
        rv_around_header.setAdapter(mAroundHeaderRecyclerViewAdapter);

        rv_around_header.addOnItemTouchListener(new RecyclerViewOnClickListener(mContext, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                isSelect.set(position, !isSelect.get(position));
                mAroundHeaderRecyclerViewAdapter.setIsSelect(isSelect);
                mAroundHeaderRecyclerViewAdapter.notifyItemChanged(position);
                RxBus.send(new AroundSelectEvent(data.get(position)));
            }
        }));

        mHomeBannerViewPagerAdapter = new HomeBannerViewPagerAdapter(fm, mContext);
        cv_banner.setAdapter(mHomeBannerViewPagerAdapter);
        cv_banner.addOnPageChangeListener(this);
    }

    public void setEventData(ArrayList<Event> eventData) {
        pageindicator.setCount(eventData.size());
        mHomeBannerViewPagerAdapter.setData(eventData);
        mHomeBannerViewPagerAdapter.notifyDataSetChanged();
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
}
