package com.tam.joblinks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tam.joblinks.models.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toan on 4/27/2016.
 */

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MessagesViewHolder>{
    private List<Message> totalMessages;
    public MessageAdapter(ArrayList<Message> totalMessages) {
        this.totalMessages = totalMessages;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MessagesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder {

        public MessagesViewHolder(View itemView) {
            super(itemView);
        }
    }

}
