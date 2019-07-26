package com.t3h.testclient.interact;

import com.t3h.testclient.model.BaseResponse;
import com.t3h.testclient.model.UserProfile;
import com.t3h.testclient.model.request.LoginRequest;
import com.t3h.testclient.ui.home.chat.FriendResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    @GET(value = "/getAllUser")
    Call<List<UserProfile>> getAllUser();

    @GET(value = "/getAllFriend")
    Call<List<FriendResponse>> getAllFriend(
            @Query("id") int id
    );
    @POST(value = "/login")
    Call<BaseResponse<UserProfile>> login(
            @Body LoginRequest request
    );

    @Multipart
    @POST("postImage")
    Call<BaseResponse<String>> upload(
            @Part MultipartBody.Part image
    );
}
