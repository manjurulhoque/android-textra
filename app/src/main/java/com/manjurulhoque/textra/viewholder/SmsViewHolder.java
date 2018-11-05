package com.manjurulhoque.textra.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.textra.ConversationActivity;
import com.manjurulhoque.textra.R;
import com.manjurulhoque.textra.Utils;
import com.manjurulhoque.textra.adapter.SmsRecyclerViewAdapter;
import com.manjurulhoque.textra.model.Message;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewBody)
    TextView textViewBody;

    public SmsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Message message, int position, final SmsRecyclerViewAdapter.OnItemClickListener listener) {
        textViewName.setText(message.getContact().getName() != null ? message.getContact().getName() : message.getContact().getNumber());
        textViewBody.setText(message.getSnippet());
        textViewTime.setText(Utils.getDate(message.getDate()));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(message);
            }
        });
    }
}
