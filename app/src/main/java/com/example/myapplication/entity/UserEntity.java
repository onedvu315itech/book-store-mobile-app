package com.example.myapplication.entity;

public class UserEntity extends BaseEntity {
    private String email;
    private String name;

    public UserEntity() {
    }

    public UserEntity(String uid, String email, String name) {
        super(uid);
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

