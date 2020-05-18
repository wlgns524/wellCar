package com.rightcode.wellcar.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rightcode.wellcar.Activity.CustomGalleryActivity;
import com.rightcode.wellcar.Activity.Login.AddressWebActivitiy;
import com.rightcode.wellcar.Activity.MainActivity;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.Log;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.user.UserStoreUpdate;
import com.rightcode.wellcar.network.model.response.user.UserCompany;
import com.rightcode.wellcar.network.model.response.user.UserStore;
import com.rightcode.wellcar.network.requester.store.StoreThumbnailRegisterRequester;
import com.rightcode.wellcar.network.requester.user.UserInfoRequester;
import com.rightcode.wellcar.network.requester.user.UserStoreUpdateRequester;
import com.rightcode.wellcar.network.responser.user.UserInfoResponser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_ACTIVITY_ACTION;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_IMAGE_COUNT;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SELECT_IMAGE;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_SINGLE_SELECT;
import static com.rightcode.wellcar.Util.ExtraData.REQUEST_CODE_GALLERY;

public class CompanyManagementCompanyInformationFragment extends BaseFragment {

    @BindView(R.id.et_company_name)
    TextView et_company_name;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_address_detail)
    AppCompatEditText et_address_detail;
    @BindView(R.id.et_company_introduction)
    EditText et_company_introduction;
    @BindView(R.id.iv_thumbnail_image)
    ImageView iv_thumbnail_image;
    @BindView(R.id.et_opens)
    EditText et_opens;

    private View root;
    private ArrayList<String> selectedPhotos;
    private UserStore userStore;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static CompanyManagementCompanyInformationFragment newInstance() {
        CompanyManagementCompanyInformationFragment f = new CompanyManagementCompanyInformationFragment();
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
        root = inflater.inflate(R.layout.fragment_company_management_company_information, container, false);

        ButterKnife.bind(this, root);
        initialize();

        return root;
    }


    //----------------------------------------------------------------------------------------------
    // Override
    //----------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EXTRA_ACTIVITY_ACTION: {
                    tv_address.setText(data.getStringExtra("result"));
                    break;
                }
                case REQUEST_CODE_GALLERY: {
                    ArrayList<String> photos = data.getStringArrayListExtra(EXTRA_SELECT_IMAGE);
                    selectedPhotos = photos;
                    Glide.with(getContext()).load(selectedPhotos.get(0))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .centerCrop()
                            .into(iv_thumbnail_image);
                    iv_thumbnail_image.setVisibility(View.VISIBLE);
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @OnClick({R.id.ll_address, R.id.tv_thumnail_attach, R.id.tv_thumnail_delete, R.id.tv_company_information_modify})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.ll_address: {
                Intent intent = new Intent(getContext(), AddressWebActivitiy.class);
                startActivityForResult(intent, EXTRA_ACTIVITY_ACTION);
                break;
            }
            case R.id.tv_thumnail_attach: {
                checkPermission();
                break;
            }
            case R.id.tv_thumnail_delete: {
                if (selectedPhotos != null)
                    selectedPhotos.clear();
                iv_thumbnail_image.setImageResource(android.R.color.transparent);
                iv_thumbnail_image.setVisibility(View.GONE);
                break;
            }
            case R.id.tv_company_information_modify: {
                if (TextUtils.isEmpty(et_company_name.getText().toString())) {
                    ToastUtil.show(getContext(), "상호명을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(tv_address.getText().toString())) {
                    ToastUtil.show(getContext(), "주소을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_address_detail.getText().toString())) {
                    ToastUtil.show(getContext(), "상세주소를 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_company_introduction.getText().toString())) {
                    ToastUtil.show(getContext(), "소개글을 입력해주세요");
                    return;
                }
                if (TextUtils.isEmpty(et_opens.getText().toString())) {
                    ToastUtil.show(getContext(), "영업시간을 입력해주세요");
                    return;
                }
                userStoreUpdate();
                break;
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        userStore = MemberManager.getInstance(getContext()).getUserInfo().getStore();
        et_company_name.setText(userStore.getName());
        tv_address.setText(userStore.getAddress());
        et_address_detail.setText(userStore.getAddressDetail());
        et_company_introduction.setText(userStore.getIntroduction());
        et_opens.setText(userStore.getOpens());
        Glide.with(getContext()).load(userStore.getThumbnail() != null ? userStore.getThumbnail().getName() : "")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .centerCrop()
                .into(iv_thumbnail_image);
    }


    private void checkPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(getContext(), CustomGalleryActivity.class);
                intent.putExtra(EXTRA_IMAGE_COUNT, 1);
                if (selectedPhotos != null) {
                    intent.putStringArrayListExtra(EXTRA_SELECT_IMAGE, selectedPhotos);
                    intent.putExtra(EXTRA_SINGLE_SELECT, true);
                }
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                ToastUtil.show(getContext(), "앨범 접근권한을 허용해주세요");
            }
        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("앨범 접근권한을 허용해주세요")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void userStoreUpdate() {
        UserStoreUpdateRequester userStoreUpdateRequester = new UserStoreUpdateRequester(getContext());

        UserStoreUpdate param = new UserStoreUpdate();
        param.setName(et_company_name.getText().toString());
        param.setAddress(tv_address.getText().toString());
        param.setAddressDetail(et_address_detail.getText().toString());
        param.setIntroduction(et_company_introduction.getText().toString());
        param.setOpens(et_opens.getText().toString());

        userStoreUpdateRequester.setParam(param);
        request(userStoreUpdateRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        if (selectedPhotos != null && selectedPhotos.size() > 0) {
                            storeThumbnailRegister();
                        } else {
                            userInfo();
                        }
                    } else {
                        hideLoading();
                        showServerErrorDialog(result.getResultMsg());
                    }
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void storeThumbnailRegister() {
        StoreThumbnailRegisterRequester storeThumbnailRegisterRequester = new StoreThumbnailRegisterRequester(getContext());
        storeThumbnailRegisterRequester.setStoreId(userStore.getId());
        storeThumbnailRegisterRequester.setPath(selectedPhotos);

        request(storeThumbnailRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        userInfo();
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                });
    }

    private void userInfo() {
        showLoading();
        UserInfoRequester userInfoRequester = new UserInfoRequester(getContext());

        request(userInfoRequester,
                success -> {
                    UserInfoResponser result = (UserInfoResponser) success;
                    if (result.getCode() == 200) {
                        MemberManager.getInstance(getContext()).updateLogInInfo(result.getUser());
                    }
                    ToastUtil.show(getContext(), "수정되었습니다");
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}

