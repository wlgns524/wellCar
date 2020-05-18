package com.rightcode.wellcar.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rightcode.wellcar.Activity.Login.LoginActivity;
import com.rightcode.wellcar.Adapter.RecyclerViewAdapter.TalkListRecyclerViewAdapter;
import com.rightcode.wellcar.Fragment.BaseFragment;
import com.rightcode.wellcar.Fragment.TopFragment;
import com.rightcode.wellcar.MemberManager;
import com.rightcode.wellcar.R;
import com.rightcode.wellcar.RxJava.RxBus;
import com.rightcode.wellcar.Util.FragmentUtil;
import com.rightcode.wellcar.network.requester.chatRoom.ChatRoomListRequester;
import com.rightcode.wellcar.network.responser.chatRoom.ChatRoomListResponser;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TalkFragment extends BaseFragment {


    @BindView(R.id.rl_not_login)
    RelativeLayout rl_not_login;
    @BindView(R.id.ll_login)
    LinearLayout ll_login;
    @BindView(R.id.rv_talk)
    RecyclerView rv_talk;

    private TopFragment mTopFragment;
    private View root;
    private Subscription subscription;

    private TalkListRecyclerViewAdapter mTalkListRecyclerViewAdapter;

    //------------------------------------------------------------------------------------------
    // contructor
    //------------------------------------------------------------------------------------------

    public static TalkFragment newInstance() {
        TalkFragment f = new TalkFragment();
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
        root = inflater.inflate(R.layout.fragment_talk, container, false);

        RxBus.register(this);
        ButterKnife.bind(this, root);
        initialize();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        initLayoutUserInfo();

    }

    @Override
    public void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

    @OnClick({R.id.tv_login})
    void onClickMenu(View view) {
        switch (view.getId()) {
            case R.id.tv_login: {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // private
    //----------------------------------------------------------------------------------------------

    private void initialize() {
        mTopFragment = (TopFragment) FragmentUtil.findFragmentByTag(getChildFragmentManager(), "TopFragment");
        mTopFragment.setImage(TopFragment.Menu.CENTER, R.drawable.title_bar_logo);
        mTopFragment.setImagePadding(TopFragment.Menu.CENTER, 10);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_talk.setLayoutManager(verticalLayoutManager);
        mTalkListRecyclerViewAdapter = new TalkListRecyclerViewAdapter(getContext());
        rv_talk.setAdapter(mTalkListRecyclerViewAdapter);
    }

    private void initLayoutUserInfo() {
        if (MemberManager.getInstance(getContext()).isLogin()) {
            ll_login.setVisibility(View.VISIBLE);
            rl_not_login.setVisibility(View.GONE);

            subscription = Observable
                    .interval(3000, TimeUnit.MILLISECONDS, Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(i -> {
                        chatRoomList();
                    });
            chatRoomList();
            ;
        } else {
            ll_login.setVisibility(View.GONE);
            rl_not_login.setVisibility(View.VISIBLE);
        }
    }

    private void chatRoomList() {
//        showLoading();
        ChatRoomListRequester chatRoomListRequester = new ChatRoomListRequester(getContext());

        request(chatRoomListRequester,
                success -> {
                    ChatRoomListResponser result = (ChatRoomListResponser) success;
                    if (result.getCode() == 200) {
                        mTalkListRecyclerViewAdapter.setData(result.getList());
                        mTalkListRecyclerViewAdapter.notifyDataSetChanged();
                    } else {
                        showServerErrorDialog(result.getResultMsg());
                    }
//                    hideLoading();
                }, fail -> {
//                    hideLoading();
                    showServerErrorDialog(fail.getResultMsg());
                });
    }
}
