package com.rightcode.wellcar.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.rightcode.wellcar.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopFragment extends BaseFragment {

    public enum Menu {
        LEFT, CENTER, RIGHT
    }

    @BindView(R.id.root_header_bar)
    LinearLayout root_header_bar;
    @BindView(R.id.iv_center)
    ImageView mCenterImage;
    @BindView(R.id.tv_center)
    TextView mCenterText;
    @BindView(R.id.iv_left)
    ImageView mLeftImage;
    @BindView(R.id.tv_left)
    TextView mLeftText;
    @BindView(R.id.iv_right)
    ImageView mRightImage;
    @BindView(R.id.tv_right)
    TextView mRightText;
    @BindView(R.id.ll_left)
    LinearLayout mLeftLinearLayout;
    @BindView(R.id.ll_center)
    LinearLayout mCenterLinearLayout;
    @BindView(R.id.ll_right)
    LinearLayout mRightLinearLayout;

    private View root;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void setImage(Menu menu, int res) {
        switch (menu) {
            case LEFT:
                mLeftImage.setImageDrawable(getResources().getDrawable(res));
                mLeftImage.setVisibility(View.VISIBLE);
                mLeftText.setVisibility(View.GONE);
                break;
            case CENTER:
                mCenterImage.setImageDrawable(getResources().getDrawable(res));
                mCenterImage.setVisibility(View.VISIBLE);
                mCenterText.setVisibility(View.GONE);
                break;
            case RIGHT:
                mRightImage.setImageDrawable(getResources().getDrawable(res));
                mRightImage.setVisibility(View.VISIBLE);
                mRightText.setVisibility(View.GONE);
                break;
        }
    }


    public void setImagePadding(Menu menu, int padding) {
        float density = getResources().getDisplayMetrics().density;
        int paddingPixel = (int) (padding * density);
        switch (menu) {
            case LEFT:
                mLeftImage.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                break;
            case CENTER:
                mCenterImage.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                break;
            case RIGHT:
                mRightImage.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                break;
        }
    }

    public void setText(Menu menu, int res) {
        switch (menu) {
            case LEFT:
                mLeftText.setText(getResources().getString(res));
                mLeftImage.setVisibility(View.GONE);
                mLeftText.setVisibility(View.VISIBLE);
                break;
            case CENTER:
                mCenterText.setText(getResources().getString(res));
                mCenterImage.setVisibility(View.GONE);
                mCenterText.setVisibility(View.VISIBLE);
                break;
            case RIGHT:
                mRightText.setText(getResources().getString(res));
                mRightImage.setVisibility(View.GONE);
                mRightText.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void setText(Menu menu, String str) {
        switch (menu) {
            case LEFT:
                mLeftText.setText(str);
                mLeftImage.setVisibility(View.GONE);
                mLeftText.setVisibility(View.VISIBLE);
                break;
            case CENTER:
                mCenterText.setText(str);
                mCenterImage.setVisibility(View.GONE);
                mCenterText.setVisibility(View.VISIBLE);
                break;
            case RIGHT:
                mRightText.setText(str);
                mRightImage.setVisibility(View.GONE);
                mRightText.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void setTextColor(Menu menu, int res) {
        switch (menu) {
            case LEFT:
                mLeftText.setTextColor(getResources().getColor(res));
                break;
            case CENTER:
                mCenterText.setTextColor(getResources().getColor(res));
                break;
            case RIGHT:
                mRightText.setTextColor(getResources().getColor(res));
                break;
        }
    }

    public void setListener(Menu menu, View.OnClickListener listener) {
        switch (menu) {
            case LEFT:
                mLeftLinearLayout.setOnClickListener(listener);
                break;
            case CENTER:
                mCenterLinearLayout.setOnClickListener(listener);
                break;
            case RIGHT:
                mRightLinearLayout.setOnClickListener(listener);
                break;
        }
    }

    //    @Override
//    public void setMenuVisibility(boolean menuVisible) {
//        super.setMenuVisibility(menuVisible);
////    }
    public void setVisible(int i) {
        if (i == View.VISIBLE) {
            root.setVisibility(View.VISIBLE);
        } else if (i == View.INVISIBLE) {
            root.setVisibility(View.INVISIBLE);
        } else {
            root.setVisibility(View.GONE);
        }
    }

    public void setBackgroundColor(int color) {
        root_header_bar.setBackgroundResource(color);
    }
}
