package com.manjurulhoque.textra.model;

import java.io.Serializable;

public class Message implements Serializable{

    private long threadId = -1;
    public long date = -1;
    private long msgCount = -1;
    private boolean read = false;
    private Contact contact;
    private String snippet = null;
    private String recipient;

    public Message() {
        threadId = -1;
        date = -1;
        msgCount = -1;
        read = false;
        contact = null;
        recipient = null;
    }

    public Message(long threadId, long date, long msgCount, boolean read, Contact contact, String snippet, String recipient) {
        this.threadId = threadId;
        this.date = date;
        this.msgCount = msgCount;
        this.read = read;
        this.contact = contact;
        this.snippet = snippet;
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(long msgCount) {
        this.msgCount = msgCount;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
