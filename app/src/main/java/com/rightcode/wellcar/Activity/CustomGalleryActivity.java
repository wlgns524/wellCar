package com.rightcode.wellcar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.rightcode.wellcar.Component.GalleryView;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SELECT_IMAGE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SINGLE_SELECT;


public class CustomGalleryActivity extends BaseActivity {


    @BindView(R.id.gv_list)
    GalleryView galleryView;
    @BindView(R.id.tv_done)
    TextView tv_done;

    private TopFragment mTopFragment;

    //------------------------------------------------------------------------------------------
    // life Cycle
    //------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_gallery);
        ButterKnife.bind(this);

        initialize();
    }

    @Override
    public void onBackPressed() {
        finishWithAnim();
    }


    @OnClick(R.id.tv_done)
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_done: {
                Intent i = new Intent();
                i.putStringArrayListExtra(EXTRA_SELECT_IMAGE, galleryView.getAdapter().getSelectedItems());
                setResult(RESULT_OK, i);
                finishWithAnim();
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "우리동네 할인찾기");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
//        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arr);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        int maxCount = 6;
        if (maxCount > 0) {
            galleryView.getAdapter().setMaxCount(
                    aVoid -> {
                        ToastUtil.show(this, String.format("사진 추가는 최대 %d장까지만 가능합니다", maxCount));
                    },
                    maxCount);
        }

        int maxSize = 15 * 1024 * 1024;
        if (maxSize > 0) {
            galleryView.getAdapter().setMaxSize(
                    aVoid -> {
                        ToastUtil.show(this, "15MB보다 큰 사진은 제출되지 않습니다.");
                    },
                    maxSize);
        }

        int totalMaxSize = 25 * 1024 * 1024;
        if (totalMaxSize > 0) {
            galleryView.getAdapter().setTotalMaxSize(
                    aVoid -> {
                        ToastUtil.show(this, "추가한 사진이 최대 파일 크기(최대 25MB)를 초과하여 사진을 제출할 수 없습니다. 파일 크기를 줄인 다음 재시도 해주세요");
                    },
                    totalMaxSize);
        }

        galleryView.getAdapter().setOnlyNumber(false);

        if (getIntent().getBooleanExtra(EXTRA_SINGLE_SELECT, false)) {
            tv_done.setVisibility(View.GONE);
            galleryView.getAdapter().setSingleAction(photo -> {
                onClickMenu(tv_done);
            });
        } else {
            galleryView.getAdapter().setMultipleAction((photo, count) -> {
                tv_done.setEnabled(count > 0);
            });

            ArrayList<String> selectedItems = getIntent().getStringArrayListExtra(EXTRA_SELECT_IMAGE);
            if (selectedItems != null && selectedItems.size() > 0) {
                for (String data : selectedItems) {
                    if (data != null) {
                        galleryView.getAdapter().addSelectedItem(data);
                    }
                }

                tv_done.setEnabled(true);
            } else {
                tv_done.setEnabled(false);
            }
        }
    }

}
