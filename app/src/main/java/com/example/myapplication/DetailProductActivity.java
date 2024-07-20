package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.entity.CartEntity;
import com.example.myapplication.entity.ProductEntity;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {
    private TextView productNameTextView, productPriceTextView, productDes, productQuantity, productBrand, productYear, quantityText;
    private ImageView productImageView;
    private Button addToCartButton, decreaseButton, increaseButton;
    private int quantity = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Initialize views
        productNameTextView = findViewById(R.id.productName);
        productPriceTextView = findViewById(R.id.productPrice);
        productImageView = findViewById(R.id.productImage);
        addToCartButton = findViewById(R.id.addToCartButton);
        productDes = findViewById(R.id.productDescription);
        productQuantity = findViewById(R.id.productQuantity);
        productBrand = findViewById(R.id.productXuatSu);
        productYear = findViewById(R.id.productNamSanxuat);
        quantityText = findViewById(R.id.quantityText);
        decreaseButton = findViewById(R.id.decreaseButton);
        increaseButton = findViewById(R.id.increaseButton);

        // Retrieve data from intent
        String productName = getIntent().getStringExtra("productName");
        double productPrice = getIntent().getDoubleExtra("productPrice", 0);
        String productImage = getIntent().getStringExtra("productImage");
        String productId = getIntent().getStringExtra("productId");
        String productDesData = getIntent().getStringExtra("productDes");
        String quantityData = getIntent().getStringExtra("productQuantity");
        String yearData = getIntent().getStringExtra("productYearOfManufacture");
        String brandData = getIntent().getStringExtra("productBrand");

        // Handle potential null values for intent extras
        if (productName == null || productImage == null || productId == null || productDesData == null ||
                quantityData == null || yearData == null || brandData == null) {
            Toast.makeText(this, "Product data is incomplete", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set default value for quantity if null
        int availableQuantity = 0;
        try {
            availableQuantity = Integer.parseInt(quantityData);
        } catch (NumberFormatException e) {
            quantityData = "0";
        }

        // Set product details to views
        productDes.setText(productDesData);
        productQuantity.setText("Còn lại: " + quantityData);
        productYear.setText("Sản xuất năm: " + yearData);
        productBrand.setText("Xuất xứ: " + brandData);

        DecimalFormat df = new DecimalFormat("0.000");
        String formattedPrice = df.format(productPrice);
        productNameTextView.setText(productName);
        productPriceTextView.setText("Giá: " + formattedPrice + " đ");

        // Initialize quantityText with the initial quantity value
        quantityText.setText(String.valueOf(quantity));

        // Load product image using Glide
        Glide.with(this)
                .load(productImage)
                .placeholder(R.drawable.error_image) // Optional placeholder
                .error(R.drawable.error_image) // Optional error image
                .into(productImageView);

        // Disable Add to Cart button if product quantity is 0
        if (availableQuantity == 0) {
            addToCartButton.setEnabled(false);
            Toast.makeText(this, "Product is out of stock", Toast.LENGTH_SHORT).show();
        }

        // Add to Cart button functionality
        addToCartButton.setOnClickListener(v -> {
            ProductEntity product = new ProductEntity();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setUrlImage(productImage);
            product.setId(productId); // Set the product ID
            product.setQuantity(quantity); // Set the selected quantity

            CartEntity.getInstance().addProduct(product);

            // Show a confirmation message
            Toast.makeText(DetailProductActivity.this, "Product added to cart", Toast.LENGTH_SHORT).show();
        });

        // Quantity increment and decrement functionality
        decreaseButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        int finalAvailableQuantity = availableQuantity;
        increaseButton.setOnClickListener(v -> {
            if (quantity < finalAvailableQuantity) {
                quantity++;
                quantityText.setText(String.valueOf(quantity));
            }
        });
    }
}