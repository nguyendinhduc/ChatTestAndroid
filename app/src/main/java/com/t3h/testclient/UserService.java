package com.t3h.testclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET(value = "/getAllUser")
    Call<List<UserProfile>> getAllUser();

    @GET(value = "/getAllFriend")
    Call<List<FriendResponse>> getAllFriend(
            @Query("id") int id
    );
}
