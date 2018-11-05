package com.manjurulhoque.textra.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.manjurulhoque.textra.R;
import com.manjurulhoque.textra.Utils;
import com.manjurulhoque.textra.model.MessageDetail;

public class ConversationViewHolder extends RecyclerView.ViewHolder {

    TextView textViewContent;
    TextView textViewTime;

    public ConversationViewHolder(View itemView) {
        super(itemView);

        textViewContent = itemView.findViewById(R.id.textViewContent);
        textViewTime = itemView.findViewById(R.id.textViewTime);
    }

    public void bind(final MessageDetail messageDetail) {
        textViewContent.setText(messageDetail.getBody());
        textViewTime.setText(Utils.getDate(messageDetail.getDate()));
    }
}
