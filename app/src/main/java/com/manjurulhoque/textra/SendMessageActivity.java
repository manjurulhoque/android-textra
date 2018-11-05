package com.manjurulhoque.textra;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.textra.adapter.ConversationRecyclerViewAdapter;
import com.manjurulhoque.textra.utils.MessageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendMessageActivity extends AppCompatActivity {

    @BindView(R.id.editTextMessage)
    EditText editTextMessage;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.textViewToRecipient)
    TextView textViewToRecipient;
    @BindView(R.id.btnContact)
    TextView btnContact;
    @BindView(R.id.relativeLayoutSelectContact)
    RelativeLayout relativeLayoutSelectContact;

    private Uri uriContact;
    private static final int REQUEST_CODE_PICK_CONTACT = 100;
    private String contactID = "", contactNumber = "", contactName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.relativeLayoutSelectContact)
    public void selectContact() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            uriContact = data.getData();
            getContact();
        }
    }

    @OnClick(R.id.btnSend)
    public void sendSMS() {
        try {
            String message = editTextMessage.getText().toString();
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(textViewToRecipient.getText().toString(), null, message, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_LONG).show();
            editTextMessage.setText("");
            startActivity(new Intent(this, MainActivity.class));
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    private void getContact() {
        // getting contact number
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                        + ContactsContract.CommonDataKinds.Phone.TYPE + " = "
                        + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID}, null);

        if (cursor.moveToFirst()) {
            contactNumber = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursor.close();

        // getting contact name
        Cursor cursorName = getContentResolver().query(uriContact, null, null, null, null);

        if (cursorName.moveToFirst()) {
            contactName = cursorName.getString(cursorName.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursorName.close();

        textViewToRecipient.setText(contactName + "\n" + contactNumber + "");
    }
}
