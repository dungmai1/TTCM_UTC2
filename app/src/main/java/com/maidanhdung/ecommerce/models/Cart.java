package com.maidanhdung.ecommerce.models;

public class Cart {
    String ProductName, Price, ImageURL;

    public Cart() {}

    public Cart(String productName, String price, String imageURL) {
        ProductName = productName;
        Price = price;
        ImageURL = imageURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
