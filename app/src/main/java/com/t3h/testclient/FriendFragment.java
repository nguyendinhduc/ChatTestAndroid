package com.t3h.testclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.91:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService =
                retrofit.create(UserService.class);
        userService.getAllFriend(2)
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
}
