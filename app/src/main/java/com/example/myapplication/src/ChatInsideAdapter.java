package com.example.myapplication.src;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.viewbinding.ViewBinding;

import com.example.myapplication.databinding.CustomLeftChatBinding;
import com.example.myapplication.databinding.CustomRightChatBinding;
import com.example.myapplication.entity.ChatEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ChatInsideAdapter extends BaseAdapter<ChatEntity, ChatInsideAdapter.ChatInsideViewHolder> {
    private static final int LEFT = 0;
    private static final int RIGHT = 1;

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("chats");

    @Override
    public ChatInsideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewBinding binding;
        if (viewType == LEFT) {
            binding = CustomLeftChatBinding.inflate(inflater, parent, false);
        } else {
            binding = CustomRightChatBinding.inflate(inflater, parent, false);
        }
        return new ChatInsideViewHolder(binding);
    }

    public DiffUtil.ItemCallback<ChatEntity> differCallback() {
        return new DiffUtil.ItemCallback<ChatEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull ChatEntity oldItem, @NonNull ChatEntity newItem) {
                return Objects.equals(oldItem.getUid(), newItem.getUid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ChatEntity oldItem, @NonNull ChatEntity newItem) {
                return Objects.equals(oldItem.getMessage(), newItem.getMessage());
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        ChatEntity chat = differ.getCurrentList().get(position);
        if (firebaseAuth.getCurrentUser() != null && Objects.equals(firebaseAuth.getCurrentUser().getUid(), chat.getSender())) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    public class ChatInsideViewHolder extends BaseAdapter.BaseItemViewHolder<ChatEntity, ViewBinding> {

        public ChatInsideViewHolder(ViewBinding binding) {
            super(binding);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void bind(ChatEntity item) {
            long timestamp = item.getTimestamp() != null ? (long) item.getTimestamp() : 0L;
            if (binding instanceof CustomLeftChatBinding) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(item.getSender());
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String userName = snapshot.child("name").getValue(String.class);
                            ((CustomLeftChatBinding) binding).tvOtherUserName.setText(userName);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });
                ((CustomLeftChatBinding) binding).tvOtherUserText.setText(item.getMessage());
                // Set timestamp
                String formattedTime = formatTimestamp(timestamp);
                ((CustomLeftChatBinding) binding).tvOtherUserTime.setText(formattedTime);
            } else if (binding instanceof CustomRightChatBinding) {
                ((CustomRightChatBinding) binding).tvUserText.setText(item.getMessage());
                String formattedTime = formatTimestamp(timestamp);
                ((CustomRightChatBinding) binding).tvUserTime.setText(formattedTime);
            }
        }

        private String formatTimestamp(long timestamp) {
            // Format timestamp into readable date or time string
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return sdf.format(new Date(timestamp));
        }
    }
}
