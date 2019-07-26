package com.t3h.testclient.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.t3h.testclient.R;
import com.t3h.testclient.model.UserProfile;
import com.t3h.testclient.interact.UserService;
import com.t3h.testclient.socket.SocketManager;
import com.t3h.testclient.ui.home.chat.ChatFunFragment;
import com.t3h.testclient.ui.start.LoginFragment;

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
        openLogin();
    }

    public void openFragmentHome(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, new ChatFunFragment(),
                        ChatFunFragment.class.getName())
                .commit();
    }

    private void openLogin(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, new LoginFragment(),
                        LoginFragment.class.getName())
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager.getInstance().disconnect();
    }
}
