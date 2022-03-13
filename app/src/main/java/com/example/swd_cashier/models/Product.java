package com.example.swd_cashier.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class Product {
    private int id;
    private int categoryId;
    private String name;
    private String unpackedProductName;
    private String unitLabel;
    private int originalPrice;
    private int eventPrice;
    private int conversionRate;
    private int quantity;
    private String sku;

    public Product(int id, int categoryId, String name, String unpackedProductName, String unitLabel, int originalPrice, int eventPrice, int conversionRate, int quantity, String sku) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.unpackedProductName = unpackedProductName;
        this.unitLabel = unitLabel;
        this.originalPrice = originalPrice;
        this.eventPrice = eventPrice;
        this.conversionRate = conversionRate;
        this.quantity = quantity;
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int storeId) {
        this.categoryId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnpackedProductName() {
        return unpackedProductName;
    }

    public void setUnpackedProductName(String unpackedProductName) {
        this.unpackedProductName = unpackedProductName;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(int eventPrice) {
        this.eventPrice = eventPrice;
    }

    public int getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(int conversionRate) {
        this.conversionRate = conversionRate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getOriginalPrice(), getOriginalPrice()) == 0 &&
                getId() == product.getId()&&
                getName().equals(product.getName());
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getId() == (newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

}
