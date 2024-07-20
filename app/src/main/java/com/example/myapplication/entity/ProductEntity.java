package com.example.myapplication.entity;

import java.util.Objects;
import java.util.UUID;

public class ProductEntity {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String brand;
    private int yearOfManufacture;
    private String category;
    private String urlImage;

    // Default Constructor
    public ProductEntity() {
        this.id = UUID.randomUUID().toString();  // Generate a random UUID for id
    }

    // Parameterized Constructor
    public ProductEntity(String name, String description, double price, int quantity, String brand, int yearOfManufacture, String category, String urlImage) {
        this.id = UUID.randomUUID().toString();  // Generate a random UUID for id
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.yearOfManufacture = yearOfManufacture;
        this.category = category;
        this.urlImage = urlImage;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    // Override equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}