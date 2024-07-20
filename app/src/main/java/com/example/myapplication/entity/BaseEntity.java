package com.example.myapplication.entity;

public class BaseEntity {
    private String uid;

    public BaseEntity() {
    }

    public BaseEntity(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

