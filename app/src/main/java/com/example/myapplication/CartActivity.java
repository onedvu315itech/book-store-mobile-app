package com.example.myapplication;

import static com.example.myapplication.R.layout.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entity.CartEntity;
import com.example.myapplication.entity.ProductEntity;
import com.example.myapplication.src.CartAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnCartChangeListener {

    private RecyclerView cartView;
    private CartAdapter cartAdapter;
    private List<ProductEntity> cartProducts;
    private TextView totaltxt, delitxt, taxtxt, totalfntxt;
    private Button checkoutButton;
    private double total;
    private static final double DELIVERY_FEE = 20.0; // Example delivery fee
    private static final double TAX = 2.0; // Example tax

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartView = findViewById(R.id.cartView);
        totaltxt = findViewById(R.id.totaltxt);
        delitxt = findViewById(R.id.delitxt);
        taxtxt = findViewById(R.id.taxtxt);
        totalfntxt = findViewById(R.id.totalfntxt);
        checkoutButton = findViewById(R.id.checkoutButton);

        // Initialize cart products from CartEntity
        cartProducts = CartEntity.getInstance().getCartProducts();

        if (cartProducts != null && !cartProducts.isEmpty()) {
            // Set up RecyclerView with CartAdapter
            cartAdapter = new CartAdapter(cartProducts, this);
            cartView.setLayoutManager(new LinearLayoutManager(this));
            cartView.setAdapter(cartAdapter);

            // Update the totals for the cart
            updateCartTotals();
        } else {
            // Handle the case where cart is empty
            handleEmptyCart();
        }

        // Handle checkout button click
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("total", total);
            intent.putExtra("deliveryFee", DELIVERY_FEE);
            intent.putExtra("tax", TAX);
            startActivity(intent);
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        super.onResume();
        // Refresh cart view when returning to this activity
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
            updateCartTotals();
        }
    }

    private synchronized void updateCartTotals() {
        total = 0;
        for (ProductEntity product : cartProducts) {
            total += product.getPrice() * product.getQuantity();
        }

        DecimalFormat df = new DecimalFormat("0.000");
        // Applying custom formatting and appending the currency unit "đ"
        String formattedTotal = df.format(total) + " đ";
        String formattedDeliveryFee = df.format(DELIVERY_FEE) + " đ";
        String formattedTax = df.format(TAX) + " đ";
        String formattedTotalFinal = df.format(total + DELIVERY_FEE + TAX) + " đ";

        totaltxt.setText(formattedTotal);
        delitxt.setText(formattedDeliveryFee);
        taxtxt.setText(formattedTax);
        totalfntxt.setText(formattedTotalFinal);

        if (cartProducts.isEmpty()) {
            handleEmptyCart();
        }
    }

    private void handleEmptyCart() {
        Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
        // Optionally, hide UI elements related to cart totals and checkout
        totaltxt.setText("");
        delitxt.setText("");
        taxtxt.setText("");
        totalfntxt.setText("");
        checkoutButton.setEnabled(false);
    }

    @Override
    public void onCartChanged() {
        updateCartTotals();
        if (cartProducts.isEmpty()) {
            handleEmptyCart();
        } else {
            checkoutButton.setEnabled(true);
        }
        sendNotification();
    }

    private void sendNotification() {
        // Retrieve current cart item count
        int itemCount = CartEntity.getInstance().getCartProducts().size();

        // Show notification badge on HomeActivity
        SharedPreferences sharedPreferences = getSharedPreferences("cart", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("itemCount", itemCount);
        editor.apply();

        // Trigger HomeActivity to update notification badge
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}