package com.manjurulhoque.textra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manjurulhoque.textra.R;
import com.manjurulhoque.textra.model.MessageDetail;
import com.manjurulhoque.textra.viewholder.ConversationViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ConversationRecyclerViewAdapter extends RecyclerView.Adapter<ConversationViewHolder> {

    private List<MessageDetail> messageDetails = new ArrayList<>();
    private Context context;

    public ConversationRecyclerViewAdapter(Context context, List<MessageDetail> messageDetails) {
        this.context = context;
        this.messageDetails = messageDetails;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layour_conversation_row, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.bind(messageDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return messageDetails.size();
    }
}
