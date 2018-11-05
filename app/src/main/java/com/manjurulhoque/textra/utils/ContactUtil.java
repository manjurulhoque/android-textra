package com.manjurulhoque.textra.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.manjurulhoque.textra.model.Contact;

public class ContactUtil {

    public static Contact getContactByRecipientId(Context context, long recipientId) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor addrCursor = contentResolver.query(Uri.parse("content://mms-sms/canonical-address/" + recipientId), null, null, null, null);
        addrCursor.moveToFirst();
        String number = addrCursor.getString(0); // we got number here
        number = number.replace(" ", "");
        number = number.replace("-", "");
        //Log.d("GET_NUMBER", number);
        Contact c = findContactByNumber(context, number);
        return c;
    }

    public static Contact findContactByNumber(Context context, String phoneNumber) {

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup.NORMALIZED_NUMBER, ContactsContract.PhoneLookup._ID};

        String name = null;
        String nPhoneNumber = phoneNumber;
        long id = 0;

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {

            if (cursor.moveToFirst()) {
                nPhoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.NORMALIZED_NUMBER));
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                id = cursor.getLong(cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            }
        }

        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(name);
        contact.setNumber(nPhoneNumber);

        return contact;
    }
}
