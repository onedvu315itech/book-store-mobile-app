package com.example.myapplication.src;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entity.CartEntity;
import com.example.myapplication.entity.ProductEntity;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ProductEntity> cartProducts;
    private final OnCartChangeListener onCartChangeListener;

    public CartAdapter(List<ProductEntity> cartProducts, OnCartChangeListener onCartChangeListener) {
        this.cartProducts = cartProducts;
        this.onCartChangeListener = onCartChangeListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductEntity product = cartProducts.get(position);

        holder.productName.setText(product.getName());
        holder.productCategory.setText(product.getCategory());

        DecimalFormat df = new DecimalFormat("0.000");
        String formattedPrice = df.format(product.getPrice());
        holder.productPrice.setText("Giá: " + formattedPrice + " đ");

        holder.productQuantity.setText(String.valueOf(product.getQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(product.getUrlImage())
                .placeholder(R.drawable.error_image) // Optional placeholder
                .error(R.drawable.error_image) // Optional error image
                .into(holder.productImage);

        holder.decreaseButton.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                holder.productQuantity.setText(String.valueOf(product.getQuantity()));
                onCartChangeListener.onCartChanged();
            }
        });

        holder.increaseButton.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            holder.productQuantity.setText(String.valueOf(product.getQuantity()));
            onCartChangeListener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productCategory, productPrice, productQuantity;
        ImageView productImage;
        Button decreaseButton, increaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            productCategory = itemView.findViewById(R.id.productCategoryTextView);
            productPrice = itemView.findViewById(R.id.productPriceTextView);
            productQuantity = itemView.findViewById(R.id.quantityText);
            productImage = itemView.findViewById(R.id.productImageView);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
        }
    }

    public interface OnCartChangeListener {
        void onCartChanged();
    }
}