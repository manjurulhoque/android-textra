package com.manjurulhoque.textra.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.manjurulhoque.textra.model.Contact;
import com.manjurulhoque.textra.model.Message;
import com.manjurulhoque.textra.model.MessageDetail;

import java.util.ArrayList;
import java.util.List;

import static android.provider.CalendarContract.CalendarCache.URI;

public class MessageUtil {

    public static List<Message> getMessages(Context context) {
        List<Message> messages = new ArrayList<Message>();
        final String[] PROJECTION = new String[]{
                "_id",
                "date",
                "message_count",
                "recipient_ids",
                "snippet", // last message
                "read",
                "type"
        };
        Uri URI = Uri.parse("content://mms-sms/conversations?simple=true");
        ContentResolver mResolver = context.getContentResolver();

        Cursor cursor = mResolver.query(URI, PROJECTION, null, null, "date DESC");
        //context.startManagingCursor(cursor);

        cursor.moveToFirst();
        do {

            Message message = new Message();
            message.setThreadId(cursor.getLong(0));
            message.setDate(cursor.getLong(1));
            message.setMsgCount(cursor.getInt(2));
            message.setRecipient(cursor.getString(3));
            message.setSnippet(cursor.getString(4));
            message.setRead(cursor.getInt(5) == 1);


            int recipient_id = cursor.getInt(3);
            Contact contact = ContactUtil.getContactByRecipientId(context, recipient_id);
            message.setContact(contact);
            messages.add(message);

            //Log.d("GET_NUMBER", String.valueOf(recipient_id));
            //Toast.makeText(context, String.valueOf(recipient_id), Toast.LENGTH_SHORT).show();

        } while (cursor.moveToNext());

        return messages;
    }

    public static List<MessageDetail> getMessagesByThreadId(Context context, long id) {
        List<MessageDetail> messageDetailsList = new ArrayList<MessageDetail>();
        final Uri URI = Uri.parse("content://sms/");
        ContentResolver mResolver = context.getContentResolver();
        String[] PROJECTION = new String[]{
                "_id",
                "thread_id",
                "address", // phone number
                "body",
                "date",
                "type",
                "read"};
        Cursor cursor = mResolver.query(URI, PROJECTION, "thread_id=" + id, null, "date DESC");

        cursor.moveToFirst();
        do {

            MessageDetail message = new MessageDetail();
            message.setId(cursor.getLong(0));
            message.setThreadId(cursor.getLong(1));
            message.setAddress(cursor.getString(2));
            message.setBody(cursor.getString(3));
            message.setDate(cursor.getLong(4));
            messageDetailsList.add(message);

        } while (cursor.moveToNext());

        return messageDetailsList;
    }
}
