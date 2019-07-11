package com.t3h.testclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private List<UserProfile> userProfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.244:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService =
                retrofit.create(UserService.class);

        userService.getAllUser().enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                System.out.println("sdfdf");
                userProfiles = response.body();
                //notifydataChange
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                System.out.println("onFailure");
                t.printStackTrace();

            }
        });


    }
}
