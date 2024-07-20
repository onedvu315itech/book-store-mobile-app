package com.example.myapplication.entity;

import com.google.firebase.database.ServerValue;

public class ChatEntity extends BaseEntity {
    private String id;
    private String message;
    private String sender;
    private String receiver;
    private Object timestamp;

    public ChatEntity() {
        // Default constructor required for calls to DataSnapshot.getValue(Chat.class)
    }

    public ChatEntity(String id, String message, String sender, String receiver) {
        this.id = id;
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

