package com.t3h.testclient.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.t3h.testclient.Constants;
import com.t3h.testclient.GlideApp;
import com.t3h.testclient.R;
import com.t3h.testclient.interact.Common;
import com.t3h.testclient.interact.UserService;
import com.t3h.testclient.model.BaseResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostImageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_post_image);
        iv = findViewById(R.id.iv_);
        findViewById(R.id.btn_post_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode){
            case 100:
                if (resultCode == RESULT_OK){
                    Cursor c = getContentResolver().query(data.getData(), new String[]{"_data"}, null, null, null);
                    if ( c == null ){
                        return;
                    }
                    c.moveToFirst();
                    String path = c.getString(c.getColumnIndex("_data"));
                    postImage(path, data.getData());
                }
                break;
        }
    }

    private void postImage(String path, Uri uri){
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(uri)),
                        new File(path)
                );
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", new File(path).getName(), requestFile);

        UserService userService = Common.getUserService();
        userService.upload(body).enqueue(new Callback<BaseResponse<String>>() {
            @Override
            public void onResponse(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                Toast.makeText(PostImageActivity.this, "Success", Toast.LENGTH_LONG).show();
                GlideApp.with(PostImageActivity.this)
                        .load(Constants.BASE_URL + "/getImage?fileName="+response.body().getData())
                        .into(iv);
            }

            @Override
            public void onFailure(Call<BaseResponse<String>> call, Throwable t) {
                Toast.makeText(PostImageActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
