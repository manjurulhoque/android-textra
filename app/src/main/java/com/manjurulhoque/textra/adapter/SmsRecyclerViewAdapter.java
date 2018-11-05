package com.manjurulhoque.textra.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manjurulhoque.textra.R;
import com.manjurulhoque.textra.model.Message;
import com.manjurulhoque.textra.model.Sms;
import com.manjurulhoque.textra.viewholder.SmsViewHolder;

import java.util.List;

public class SmsRecyclerViewAdapter extends RecyclerView.Adapter<SmsViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Message message);
    }

    private List<Message> messages;
    private Context context;
    private final OnItemClickListener listener;

    public SmsRecyclerViewAdapter(List<Message> messages, Context context, OnItemClickListener listener) {
        this.messages = messages;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sms_row, parent, false);
        return new SmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsViewHolder holder, int position) {
        holder.bind(messages.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
