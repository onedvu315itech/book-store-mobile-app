package com.example.myapplication.src;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseAdapter.BaseItemViewHolder<T, ?>>
        extends RecyclerView.Adapter<VH> {

    private OnItemClickListener<T> onItemClickListener;
    private OnSetItemOrderListener<T> setItemOrderListener;

    protected abstract DiffUtil.ItemCallback<T> differCallback();

    protected final AsyncListDiffer<T> differ = new AsyncListDiffer<>(this, differCallback());

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        T item = differ.getCurrentList().get(position);
        holder.bind(item);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> {
                onItemClickListener.onItemClick(item);
            });
        }
    }

    public void submitList(List<T> list) {
        if (setItemOrderListener != null) {
            setItemOrderListener.onSetItemOrder(list);
        }
        differ.submitList(list);
        notifyDataSetChanged();
    }

    public void setItemOrderListener(OnSetItemOrderListener<T> listener) {
        this.setItemOrderListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item);
    }

    public interface OnSetItemOrderListener<T> {
        void onSetItemOrder(List<T> list);
    }

    public abstract static class BaseItemViewHolder<T, VB extends ViewBinding>
            extends RecyclerView.ViewHolder {

        protected final VB binding;
        protected final Context context;

        public BaseItemViewHolder(@NonNull VB binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = binding.getRoot().getContext();
        }

        public abstract void bind(T item);
    }
}
