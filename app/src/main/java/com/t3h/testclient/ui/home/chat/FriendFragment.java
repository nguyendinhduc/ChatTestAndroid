package com.t3h.testclient.ui.home.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.t3h.testclient.R;
import com.t3h.testclient.interact.Common;
import com.t3h.testclient.interact.CommonData;
import com.t3h.testclient.interact.UserService;
import com.t3h.testclient.ui.chat.ChatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendFragment extends Fragment implements FriendAdapter.IFriend {
    private RecyclerView rcFriend;
    private List<FriendResponse> friendResponses;
    private FriendAdapter adapter;
    private  UserService userService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friend, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcFriend = view.findViewById(R.id.rc_friend);
        rcFriend.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FriendAdapter(this);
        rcFriend.setAdapter(adapter);
        getAllFriend();
    }

    private void getAllFriend(){
       userService = Common.getUserService();
        userService.getAllFriend(
                CommonData.getInstance()
                        .getUserProfile().getId())
                .enqueue(new Callback<List<FriendResponse>>() {
                    @Override
                    public void onResponse(Call<List<FriendResponse>> call, Response<List<FriendResponse>> response) {
                        friendResponses = response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<List<FriendResponse>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public int getCount() {
        if (friendResponses == null ){
            return 0;
        }
        return friendResponses.size();
    }

    @Override
    public FriendResponse getItem(int position) {
        return friendResponses.get(position);
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent();
        intent.setClass(getContext(),
                ChatActivity.class);
        intent.putExtra("FRIEND",
                friendResponses.get(position));
        startActivity(intent);
    }
}
