package com.rightcode.wellcar.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.ImageRecyclerViewAdapter;
import com.rightcode.wellcar.Component.RatingStarLayout;
import com.rightcode.wellcar.Component.RecyclerViewOnClickListener;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.storeReview.StoreReviewRegister;
import com.rightcode.wellcar.network.model.response.storeReview.StoreReview;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewCheckRequester;
import com.rightcode.wellcar.network.requester.storeReview.StoreReviewRegisterRequester;
import com.rightcode.wellcar.network.requester.storeReviewImage.StoreReviewImageRegisterRequester;
import com.rightcode.wellcar.network.responser.storeReview.StoreReviewRegisterResponser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_DATA;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_REVIEW_TYPE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SELECT_IMAGE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SINGLE_SELECT;
import static com.rightcode.wellcar.Util.ExtraData.REQUEST_CODE_GALLERY;

public class ReviewWriteActivity extends BaseActivity {

    @BindView(R.id.rs_satisfaction)
    RatingStarLayout rs_satisfaction;
    @BindView(R.id.tv_grade_satisfaction)
    TextView tv_grade_satisfaction;
    @BindView(R.id.rs_kindness)
    RatingStarLayout rs_kindness;
    @BindView(R.id.tv_grade_kindness)
    TextView tv_grade_kindness;
    @BindView(R.id.et_review_content)
    EditText et_review_content;
    @BindView(R.id.rv_review_image)
    RecyclerView rv_review_image;

    private TopFragment mTopFragment;
    private ImageRecyclerViewAdapter mImageRecyclerViewAdapter;
    private ArrayList<String> selectedPhotos;
    private Integer storeId;
    private StoreReview data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        ButterKnife.bind(this);
        initialize();
    }

    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_GALLERY: {
                    ArrayList<String> photos = data.getStringArrayListExtra(EXTRA_SELECT_IMAGE);
                    selectedPhotos = photos;
                    mImageRecyclerViewAdapter.setData(photos);
                    mImageRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //----------------------------------------------------------------------------------------------
    // OnClick
    //----------------------------------------------------------------------------------------------

    @OnClick({R.id.tv_review_register})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_review_register: {
                if (TextUtils.isEmpty(et_review_content.getText().toString())) {
                    ToastUtil.show(ReviewWriteActivity.this, "내용을 입력해주세요");
                    break;
                }
                storeReviewRegister();
                break;
            }
        }
    }


    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");
        mTopFragment.setText(TopFragment.Menu.CENTER, "리뷰 작성");
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });

        rs_satisfaction.setRating(5.0f);
        rs_kindness.setRating(5.0f);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(ReviewWriteActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rv_review_image.setLayoutManager(verticalLayoutManager);
        mImageRecyclerViewAdapter = new ImageRecyclerViewAdapter(ReviewWriteActivity.this);
        rv_review_image.setAdapter(mImageRecyclerViewAdapter);
        rv_review_image.addOnItemTouchListener(new RecyclerViewOnClickListener(ReviewWriteActivity.this, new RecyclerViewOnClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkPermission();
            }
        }));

        rs_satisfaction.setRatingListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 0.5) {
                    rating = 0.5f;
                    ratingBar.setRating(rating);
                }
                tv_grade_satisfaction.setText(String.format("(%1.1f/5)", rating));
            }
        });
        rs_kindness.setRatingListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 0.5) {
                    rating = 0.5f;
                    ratingBar.setRating(rating);
                }
                tv_grade_kindness.setText(String.format("(%1.1f/5)", rating));
            }
        });

        if (getIntent() != null) {
            storeId = getIntent().getIntExtra(EXTRA_COMPANY_ID, -1);
            data = (StoreReview) getIntent().getSerializableExtra(EXTRA_REVIEW_DATA);

            storeReviewCheck(storeId);
        }
    }

    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(ReviewWriteActivity.this, CustomGalleryActivity.class);
                if (selectedPhotos != null) {
                    intent.putStringArrayListExtra(EXTRA_SELECT_IMAGE, selectedPhotos);
                    intent.putExtra(EXTRA_SINGLE_SELECT, false);
                }
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                ToastUtil.show(ReviewWriteActivity.this, "앨범 접근권한을 허용해주세요");
            }
        };

        TedPermission.with(ReviewWriteActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("앨범 접근권한을 허용해주세요")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void storeReviewCheck(Integer storeId) {
        showLoading();
        StoreReviewCheckRequester storeReviewCheckRequester = new StoreReviewCheckRequester(ReviewWriteActivity.this);
        storeReviewCheckRequester.setStoreId(storeId);

        request(storeReviewCheckRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {

                    } else {
                        showServerErrorDialog(result.getResultMsg(), action -> {
                            finishWithAnim();
                        });
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg(), action -> {
                        finishWithAnim();
                    });
                });
    }

    private void storeReviewRegister() {
        showLoading();
        StoreReviewRegisterRequester storeReviewRegisterRequester = new StoreReviewRegisterRequester(ReviewWriteActivity.this);
        storeReviewRegisterRequester.setStoreId(storeId);

        StoreReviewRegister param = new StoreReviewRegister();
        param.setSatisfaction(rs_satisfaction.getRating());
        param.setKindness(rs_kindness.getRating());
        param.setContent(et_review_content.getText().toString());

        storeReviewRegisterRequester.setParam(param);

        request(storeReviewRegisterRequester,
                success -> {
                    StoreReviewRegisterResponser result = (StoreReviewRegisterResponser) success;
                    if (result.getCode() == 200) {
                        if (mImageRecyclerViewAdapter.getItemCount() > 0)
                            storeReviewImageRegister(result.getStoreReviewId());
                    } else {
                        hideLoading();
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg(), action -> {
                        finishWithAnim();
                    });
                });
    }

    private void storeReviewImageRegister(Integer storeReviewId) {
        StoreReviewImageRegisterRequester storeReviewImageRegisterRequester = new StoreReviewImageRegisterRequester(ReviewWriteActivity.this);
        storeReviewImageRegisterRequester.setStoreReviewId(storeReviewId);
//        storeReviewImageRegisterRequester.setPath(mImageRecyclerViewAdapter.getItemCount());

        request(storeReviewImageRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(ReviewWriteActivity.this, "등록되었습니다");
                        finishWithAnim();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg(), action -> {
                        finishWithAnim();
                    });
                });
    }
}
