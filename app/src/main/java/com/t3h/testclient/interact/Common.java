package com.t3h.testclient.interact;

//import com.squareup.okhttp.OkHttpClient;

import com.google.gson.Gson;
import com.t3h.testclient.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Common {
    public static UserService getUserService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(new OkHttpClient.Builder().callTimeout(60 * 5, TimeUnit.SECONDS).connectTimeout(60 * 5, TimeUnit.SECONDS)
                        .readTimeout(60 * 5, TimeUnit.SECONDS).writeTimeout(60 * 5, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        UserService userService =
                retrofit.create(UserService.class);
        return userService;
    }
}
