package com.manjurulhoque.textra.model;

import java.io.Serializable;

public class MessageDetail implements Serializable {

    private long threadId = -1;
    private long id = -1;
    private int type;
    private String body;
    private long date;
    private String address;  // number
    private boolean read = false;

    public MessageDetail() {
    }

    public MessageDetail(long threadId, long id, int type, String body, long date, String address, boolean read) {
        this.threadId = threadId;
        this.id = id;
        this.type = type;
        this.body = body;
        this.date = date;
        this.address = address;
        this.read = read;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
