package com.manjurulhoque.textra;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manjurulhoque.textra.adapter.SmsRecyclerViewAdapter;
import com.manjurulhoque.textra.model.Contact;
import com.manjurulhoque.textra.model.Message;
import com.manjurulhoque.textra.model.Sms;
import com.manjurulhoque.textra.utils.MessageUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // Initialize
    @BindView(R.id.recyclerViewSms)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    // Global variables
    List<Sms> lstSms = new ArrayList<Sms>();
    List<Message> messages = new ArrayList<Message>();
    SmsRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            checkPermission();
        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        messages = MessageUtil.getMessages(this);
        progress.dismiss();

        initRecyclerView();
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        startActivity(new Intent(this, SendMessageActivity.class));
    }

    private void initRecyclerView() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerViewAdapter = new SmsRecyclerViewAdapter(messages, getApplicationContext(), new SmsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Message message) {
                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                intent.putExtra("conversation", message);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void checkPermission() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE,
                Manifest.permission.INTERNET
        };

        if (!Utils.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }
                    }
                    // Show permissionsDenied
                    //updateViews();
                }
                return;
            }
        }
    }

    public List<Sms> getAllSms() {

        Sms objSms = new Sms();
        Uri uri = Uri.parse("content://sms");
        //Uri uri =  Uri.parse("content://mms-sms/conversations?simple=true");
        ContentResolver cr = getApplicationContext().getContentResolver();

        Cursor c = cr.query(uri, null, null, null, null);
        startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        c.close();

        return lstSms;
    }
}
