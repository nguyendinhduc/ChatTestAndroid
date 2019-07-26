package com.t3h.testclient.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.t3h.testclient.GlideApp;
import com.t3h.testclient.R;
import com.t3h.testclient.interact.CommonData;
import com.t3h.testclient.model.MessageChatResponse;
import com.t3h.testclient.socket.ReciverMessage;
import com.t3h.testclient.socket.SocketManager;
import com.t3h.testclient.ui.home.chat.FriendResponse;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.IChat, View.OnClickListener, ReciverMessage {
    private RecyclerView rc;
    private EditText edtSend;
    private ChatAdapter adapter;
    private List<MessageChatResponse> messages;
    private FriendResponse friendResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rc = findViewById(R.id.rc);
        edtSend = findViewById(R.id.edt_send);
        rc.setLayoutManager(new LinearLayoutManager(this));
        messages = new ArrayList<>();
        adapter = new ChatAdapter(this);
        rc.setAdapter(adapter);
        friendResponse =
                (FriendResponse) getIntent()
                        .getSerializableExtra(
                                "FRIEND");
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_back).setOnClickListener(this);
        init();

        SocketManager.getInstance()
                .register(this);
    }

    private void init() {
        if (friendResponse.getFriendAvatar() != null) {
            GlideApp.with(this)
                    .load(friendResponse.getFriendAvatar())
                    .error(R.drawable.cake)
                    .placeholder(R.drawable.cake)
                    .into((ImageView) findViewById(R.id.iv_avatar));
        }
        ((TextView) findViewById(R.id.tv_name))
                .setText(friendResponse.getFriendName());
    }

    @Override
    public int getCount() {
        if (messages == null) {
            return 0;
        }
        return messages.size();
    }

    @Override
    public MessageChatResponse getData(int position) {
        return messages.get(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                sendMessage();
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void sendMessage() {
        MessageChatResponse message = new MessageChatResponse();
        message.setReceiverId(friendResponse.getId());
        message.setSenderId(
                CommonData.getInstance().getUserProfile().getId()
        );
        message.setContent(edtSend.getText().toString());
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
        rc.scrollToPosition(messages.size());

        SocketManager.getInstance().sendMessage(
                new Gson().toJson(message)
        );
        edtSend.setText("");
    }

    @Override
    public void recieve(final MessageChatResponse response) {
        if (response.getSenderId() !=
                friendResponse.getFriendId()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int send = response.getReceiverId();
                response.setReceiverId(CommonData.getInstance().getUserProfile().getId());
                response.setSenderId(friendResponse.getFriendId());
                messages.add(response);
                adapter.notifyItemInserted(messages.size() - 1);
                rc.scrollToPosition(messages.size());
            }
        });

    }

    @Override
    protected void onDestroy() {
        SocketManager.getInstance().unregister(this);
        super.onDestroy();
    }
}
