package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.ChatEntity;
import com.example.myapplication.src.ChatInsideAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView rvChat;
    private EditText etInput;
    private ImageView ivSend;
    private ChatInsideAdapter chatAdapter;
    private List<ChatEntity> chatList;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        FirebaseUser currentUser = createFakeFirebaseUser();
        if (currentUser == null) {
            Intent intent = new Intent(ChatActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Khởi tạo Firebase
            mDatabase = FirebaseDatabase.getInstance().getReference().child("chats");
            mAuth = FirebaseAuth.getInstance();

            // Ánh xạ view
            rvChat = findViewById(R.id.rvChat);
            etInput = findViewById(R.id.etInput);
            ivSend = findViewById(R.id.ivSend);

            // Thiết lập RecyclerView và Adapter
            chatList = new ArrayList<>();
            chatAdapter = new ChatInsideAdapter();
            rvChat.setLayoutManager(new LinearLayoutManager(this));
            rvChat.setAdapter(chatAdapter);

            // Sự kiện gửi tin nhắn
            ivSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
                }
            });

            // Lắng nghe tin nhắn từ Firebase
            listenForMessages();
        }
    }

    private void sendMessage() {
        // Lấy thông tin người dùng hiện tại
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String senderId = currentUser.getUid();
            String senderName = currentUser.getDisplayName(); // Nếu cần hiển thị tên người dùng

            String receiverId = "receiver_id"; // Thay bằng logic lấy ID của người nhận

            String messageText = etInput.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Tạo tin nhắn mới và lưu vào Firebase Realtime Database
                String messageId = UUID.randomUUID().toString();
                long timestamp = System.currentTimeMillis();
                ChatEntity chat = new ChatEntity(messageId, messageText, senderId, receiverId);
                mDatabase.child(messageId).setValue(chat);

                // Xóa nội dung tin nhắn sau khi gửi
                etInput.setText("");
            }
        }
    }

    private void listenForMessages() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                ChatEntity chat = dataSnapshot.getValue(ChatEntity.class);
                if (chat != null) {
                    chatList.add(chat);
                    chatAdapter.submitList(chatList);
                    rvChat.smoothScrollToPosition(chatList.size() - 1); // Cuộn xuống cuối danh sách tin nhắn
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}