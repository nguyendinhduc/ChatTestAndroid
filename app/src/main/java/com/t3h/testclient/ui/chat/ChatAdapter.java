package com.t3h.testclient.ui.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.t3h.testclient.R;
import com.t3h.testclient.interact.CommonData;
import com.t3h.testclient.model.MessageChatResponse;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private IChat inter;

    public ChatAdapter(IChat inter) {
        this.inter = inter;
    }

    @Override
    public int getItemViewType(int position) {
        if (inter.getData(position).getSenderId()
                == CommonData.getInstance().getUserProfile().getId()) {
            return 0;
        } else {
            return 100;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup viewGroup,
                       int itemViewType) {
        if (itemViewType == 0) {
            return new SendViewHolder(
                    LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_text_send, viewGroup, false)
            );
        }
        return new ReceiverViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_text_recieve, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder viewHolder, int position) {
        MessageChatResponse message = inter.getData(position);

        if (getItemViewType(position) == 0) {
            SendViewHolder holder = (SendViewHolder) viewHolder;
            holder.tvContent.setText(message.getContent());
        } else {
            ReceiverViewHolder holder = (ReceiverViewHolder) viewHolder;
            holder.tvContent.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return inter.getCount();
    }

    public interface IChat {
        int getCount();

        MessageChatResponse getData(int position);
    }

    static class SendViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvContent;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivAvatar;
        private TextView tvContent;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
