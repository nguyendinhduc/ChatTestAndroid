package com.t3h.testclient.ui.start;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.t3h.testclient.R;
import com.t3h.testclient.interact.Common;
import com.t3h.testclient.interact.CommonData;
import com.t3h.testclient.interact.UserService;
import com.t3h.testclient.model.BaseResponse;
import com.t3h.testclient.model.UserProfile;
import com.t3h.testclient.model.request.LoginRequest;
import com.t3h.testclient.socket.SocketManager;
import com.t3h.testclient.ui.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText edtUsername, edtPassword;
    private UserService userService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment,
                container, false);
        userService = Common.getUserService();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = view.findViewById(R.id.edt_username);
        edtPassword = view.findViewById(R.id.edt_password);
        view.findViewById(R.id.btn_login)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        LoginRequest request = new LoginRequest();
        request.setPassword(edtPassword.getText().toString());
        request.setUsername(edtUsername.getText().toString());
        userService.login(request)
                .enqueue(new Callback<BaseResponse<UserProfile>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserProfile>> call,
                                           Response<BaseResponse<UserProfile>>
                                                   response) {
                        if (response.body().getStatus() != 1){
                            Toast.makeText(getContext(), response.body().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }else {
                            loginSuccess(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserProfile>> call, Throwable t) {

                    }
                });
    }

    private void loginSuccess(UserProfile userProfile){
//        Toast.makeText(getContext(),"Login success", Toast.LENGTH_SHORT)
//                .show();

        CommonData.getInstance().setUserProfile(userProfile);
        SocketManager.getInstance().connect();
        ((MainActivity)getActivity()).openFragmentHome();
    }


}
