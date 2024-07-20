package com.example.myapplication.src;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DetailProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.ProductEntity;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<ProductEntity> productList;
    private final Context context;

    public ProductAdapter(List<ProductEntity> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void updateList(List<ProductEntity> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        return new ProductViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductEntity currentProduct = productList.get(position);
        holder.productName.setText(currentProduct.getName());

        double price = currentProduct.getPrice();
        DecimalFormat df = new DecimalFormat("0.000");
        String formattedPrice = df.format(price);

        holder.productPrice.setText(formattedPrice + " đ");
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(currentProduct.getUrlImage()))
                .placeholder(R.drawable.error_image) // Optional
                .error(R.drawable.error_image) // Optional
                .into(holder.productImage);
        Log.v("TAGV", currentProduct.toString());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailProductActivity.class);
            intent.putExtra("productId", currentProduct.getId()); // Include productId
            intent.putExtra("productName", currentProduct.getName());
            intent.putExtra("productPrice", currentProduct.getPrice());
            intent.putExtra("productImage", currentProduct.getUrlImage());
            intent.putExtra("productDes", currentProduct.getDescription());
            intent.putExtra("productQuantity", String.valueOf(currentProduct.getQuantity()));
            intent.putExtra("productBrand", currentProduct.getBrand());
            intent.putExtra("productYearOfManufacture", String.valueOf(currentProduct.getYearOfManufacture()));

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public ImageView productImage;
        public TextView productPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.courseName);
            productImage = itemView.findViewById(R.id.courseImg);
            productPrice = itemView.findViewById(R.id.coursePrice);
        }
    }
}