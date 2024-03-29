package com.t3h.testclient.ui.home.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.t3h.testclient.GlideApp;
import com.t3h.testclient.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.HolderFriend> {
    private IFriend inter;
    public FriendAdapter(IFriend inter){
        this.inter = inter;
    }
    @NonNull
    @Override
    public HolderFriend onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HolderFriend(
                LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_friend, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderFriend holderFriend, int i) {
        FriendResponse data = inter.getItem(i);
        GlideApp.with(holderFriend.ivAvatar)
                .load(data.getFriendAvatar())
                .into(holderFriend.ivAvatar);
        holderFriend.tvUsername.setText(
                data.getFriendUsername()
        );
        holderFriend.tvName.setText(
                data.getFriendName()
        );
        holderFriend.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inter.onClickItem(holderFriend.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    public interface IFriend{
        int getCount();
        FriendResponse getItem(int position);
        void onClickItem(int position);
    }

    static class HolderFriend extends RecyclerView.ViewHolder{
        private ImageView ivAvatar;
        private TextView tvName, tvUsername;
        public HolderFriend(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvUsername = itemView.findViewById(R.id.tv_username);
        }
    }
}
