package com.manjurulhoque.textra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.manjurulhoque.textra.adapter.ConversationRecyclerViewAdapter;
import com.manjurulhoque.textra.model.Message;
import com.manjurulhoque.textra.model.MessageDetail;
import com.manjurulhoque.textra.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationActivity extends AppCompatActivity {

    // Initialize
    @BindView(R.id.recyclerViewConversation)
    RecyclerView mRecyclerView;
    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.buttonSend)
    ImageButton buttonSend;

    private Message conversation;
    private List<MessageDetail> messageDetailsList = new ArrayList<MessageDetail>();
    private ConversationRecyclerViewAdapter recyclerViewAdapter;
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        conversation = (Message) getIntent().getExtras().getSerializable("conversation");
        messageDetailsList = MessageUtil.getMessagesByThreadId(this, conversation.getThreadId());
        //Toast.makeText(this, messageDetailsList.get(0).getAddress(), Toast.LENGTH_SHORT).show();
        phoneNumber = messageDetailsList.get(0).getAddress();

        initRecyclerView();
    }

    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new ConversationRecyclerViewAdapter(getApplicationContext(), messageDetailsList);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    @OnClick(R.id.buttonSend)
    public void sendSMS() {
        try {
            String message = editTextMessage.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            messageDetailsList = MessageUtil.getMessagesByThreadId(this, conversation.getThreadId());
            recyclerViewAdapter = new ConversationRecyclerViewAdapter(getApplicationContext(), messageDetailsList);
            mRecyclerView.setAdapter(recyclerViewAdapter);
            editTextMessage.setText("");
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
