package com.rightcode.wellcar.Activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.TalkDetailRecyclerViewAdapter;
import com.rightcode.wellcar.Dialog.CommonDialog;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.Util.CommonUtil;
import com.rightcode.wellcar.Util.DataEnums;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.Util.ToastUtil;
import com.rightcode.wellcar.network.model.CommonResult;
import com.rightcode.wellcar.network.model.request.chat.ChatRegister;
import com.rightcode.wellcar.network.requester.chat.ChatRegisterRequester;
import com.rightcode.wellcar.network.requester.chatRoom.ChatRoomDetailRequester;
import com.rightcode.wellcar.network.requester.chatRoom.ChatRoomRemoveRequester;
import com.rightcode.wellcar.network.responser.chatRoom.ChatRoomDetailResponser;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_ID;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_COMPANY_NAME;
import static com.rightcode.wellcar.Util.ExtraData.EXTRA_TALK_ID;

public class TalkDetailCustomerActivity extends BaseActivity {

    @BindView(R.id.rv_talk_detail)
    RecyclerView rv_talk_detail;
    @BindView(R.id.et_talk_send)
    EditText et_talk_send;
    @BindView(R.id.iv_talk_send)
    ImageView iv_talk_send;
    @BindView(R.id.iv_bottom)
    ImageView iv_bottom;

    private TopFragment mTopFragment;
    private TalkDetailRecyclerViewAdapter mTalkDetailRecyclerViewAdapter;
    private Integer chatRoomId;
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_detail_customer);

        ButterKnife.bind(this);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscription = Observable
                .interval(3000, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> {
                    if (chatRoomId != null) {
                        chatRoomDetail(false);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @OnFocusChange(R.id.et_talk_send)
//    void inputFocusChanged (boolean hasFocus) {
//        if(hasFocus){
//            showKeyboard(et_talk_send);
//            scrollBottom();
//        }else{
//            hideKeyboard(et_talk_send);
//        }
//    }

    @OnTextChanged(R.id.et_talk_send)
    void onTextChanged(CharSequence text) {
        if (TextUtils.isEmpty(et_talk_send.getText())) {
            iv_talk_send.setImageDrawable(getDrawable(R.drawable.ic_talk_unsend));
        } else {
            iv_talk_send.setImageDrawable(getDrawable(R.drawable.ic_talk_send));
        }
    }

    @OnClick({R.id.iv_talk_send, R.id.iv_bottom})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.iv_talk_send: {
                if (TextUtils.isEmpty(et_talk_send.getText())) {
                    ToastUtil.show(TalkDetailCustomerActivity.this, "내용을 입력해주세요");
                    break;
                }
                chatRegister(et_talk_send.getText().toString());
                break;
            }
            case R.id.iv_bottom:{
                scrollBottom();
            }
        }
    }

    //------------------------------------------------------------------------------------------
    // private
    //------------------------------------------------------------------------------------------
    private void initialize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getSupportFragmentManager(), "TopFragment");

        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);
        mTopFragment.setImage(TopFragment.Menu.LEFT, R.drawable.arrow_left);
        mTopFragment.setImagePadding(TopFragment.Menu.LEFT, 5);
        mTopFragment.setListener(TopFragment.Menu.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithAnim();
            }
        });
        mTopFragment.setText(TopFragment.Menu.RIGHT, "나가기");
        mTopFragment.setTextColor(TopFragment.Menu.RIGHT, R.color.red);
        mTopFragment.setListener(TopFragment.Menu.RIGHT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatRoomRemoveDialog();
            }
        });

        if (getIntent() != null) {
            chatRoomId = getIntent().getIntExtra(EXTRA_TALK_ID, -1);
            mTopFragment.setText(TopFragment.Menu.CENTER, getIntent().getStringExtra(EXTRA_COMPANY_NAME));
            chatRoomDetail(true);
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(TalkDetailCustomerActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_talk_detail.setLayoutManager(verticalLayoutManager);
        mTalkDetailRecyclerViewAdapter = new TalkDetailRecyclerViewAdapter(TalkDetailCustomerActivity.this);
        rv_talk_detail.setAdapter(mTalkDetailRecyclerViewAdapter);
        mTalkDetailRecyclerViewAdapter.setCompanyId(getIntent().getIntExtra(EXTRA_COMPANY_ID, -1));
        rv_talk_detail.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom > bottom) {
                    rv_talk_detail.scrollBy(0, oldBottom - bottom);
                }
            }
        });
        rv_talk_detail.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    CommonUtil.hideKeyPad(et_talk_send);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        rv_talk_detail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    iv_bottom.setVisibility(View.GONE);
                }
            }
        });
    }

    private void chatRoomDetail(Boolean isScrollBottom) {
        ChatRoomDetailRequester chatRoomDetailRequester = new ChatRoomDetailRequester(TalkDetailCustomerActivity.this);
        chatRoomDetailRequester.setId(chatRoomId);

        request(chatRoomDetailRequester,
                success -> {
                    ChatRoomDetailResponser result = (ChatRoomDetailResponser) success;
                    if (result.getCode() == 200) {
                        Integer oldCount = mTalkDetailRecyclerViewAdapter.getItemCount();
                        mTalkDetailRecyclerViewAdapter.setData(result.getData().getChats());
                        mTalkDetailRecyclerViewAdapter.notifyDataSetChanged();
                        if (isScrollBottom)
                            scrollBottom();
                        if (oldCount < result.getData().getChats().size() && !isScrollBottom)
                            iv_bottom.setVisibility(View.VISIBLE);
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                        subscription.unsubscribe();
                    }
                }, fail -> {
                    subscription.unsubscribe();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void chatRegister(String content) {
        showLoading();
        ChatRegisterRequester chatRegisterRequester = new ChatRegisterRequester(TalkDetailCustomerActivity.this);

        ChatRegister param = new ChatRegister();
        param.setContent(content);
        param.setDiff(DataEnums.TalkDiffType.Chatting);

        chatRegisterRequester.setChatRoomId(chatRoomId);
        chatRegisterRequester.setParam(param);

        request(chatRegisterRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        chatRoomDetail(true);
                        et_talk_send.setText("");
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }

    private void scrollBottom() {
        if (rv_talk_detail.getAdapter() != null && rv_talk_detail.getAdapter().getItemCount() > 1)
            rv_talk_detail.scrollToPosition(rv_talk_detail.getAdapter().getItemCount() - 1);
        iv_bottom.setVisibility(View.GONE);
    }

    private void chatRoomRemoveDialog() {
        CommonDialog commonDialog = new CommonDialog(TalkDetailCustomerActivity.this);

        commonDialog.setMessage("채팅방을 삭제하시겠습니까 ?\n삭제하시면 대화내용 복원이 불가능합니다");
        commonDialog.setNegativeButton("취소", cancel -> {
            commonDialog.dismiss();
        });
        commonDialog.setPositiveButton("확인", ok -> {
            commonDialog.dismiss();
            chatRoomRemove();
        });
        commonDialog.show();
    }

    private void chatRoomRemove() {
        showLoading();
        ChatRoomRemoveRequester chatRoomRemoveRequester = new ChatRoomRemoveRequester(TalkDetailCustomerActivity.this);
        chatRoomRemoveRequester.setId(chatRoomId);

        request(chatRoomRemoveRequester,
                success -> {
                    CommonResult result = (CommonResult) success;
                    if (result.getCode() == 200) {
                        ToastUtil.show(TalkDetailCustomerActivity.this, "채팅방이 삭제되었습니다");
                        finishWithAnim();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
                    hideLoading();
                }, fail -> {
                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
