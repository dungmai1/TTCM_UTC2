package com.maidanhdung.ecommerce.models;

import java.io.Serializable;

public class Products implements Serializable {
    String ProductName, ImageProduct, Description;
    int Price;
    public Products() {
    }

    public Products(String productName, String imageProduct, String description, int price) {
        ProductName = productName;
        ImageProduct = imageProduct;
        Description = description;
        Price = price;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }


}
