package com.example.myapplication.entity;

import com.example.myapplication.src.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartEntity {
    private static CartEntity instance;
    private final List<ProductEntity> cartProducts;

    private CartAdapter.OnCartChangeListener cartChangeListener;

    private CartEntity() {
        cartProducts = new ArrayList<>();
    }

    public static CartEntity getInstance() {
        if (instance == null) {
            instance = new CartEntity();
        }
        return instance;
    }

    public List<ProductEntity> getCartProducts() {
        return cartProducts;
    }

    public void addProduct(ProductEntity product) {
        if (product == null || product.getId() == null) {
            throw new IllegalArgumentException("Product or product ID cannot be null");
        }

        boolean productExists = false;
        for (ProductEntity cartProduct : cartProducts) {
            if (cartProduct.equals(product)) {
                cartProduct.setQuantity(cartProduct.getQuantity() + product.getQuantity());
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            cartProducts.add(product);
        }
    }

    public void removeProduct(ProductEntity product) {
        cartProducts.removeIf(cartProduct -> cartProduct.equals(product));
    }

    public void clearCart() {
        cartProducts.clear();
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (ProductEntity product : cartProducts) {
            totalQuantity += product.getQuantity();
        }
        return totalQuantity;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (ProductEntity product : cartProducts) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        return totalPrice;
    }

    public void updateCart(List<ProductEntity> products) {
        cartProducts.clear();
        cartProducts.addAll(products);
        notifyCartChanged();
    }

    // Method to set the cart change listener
    public void setOnCartChangeListener(CartAdapter.OnCartChangeListener listener) {
        this.cartChangeListener = listener;
    }

    // Method to notify cart change to listener
    private void notifyCartChanged() {
        if (cartChangeListener != null) {
            cartChangeListener.onCartChanged();
        }
    }
}