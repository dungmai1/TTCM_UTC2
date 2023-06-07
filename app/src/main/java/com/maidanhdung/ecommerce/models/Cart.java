package com.maidanhdung.ecommerce.models;

public class Cart {
    String productName, price, imageURL, Quality;

    public Cart() {}

    public Cart(String productName, String price, String imageURL, String quality) {
        this.productName = productName;
        this.price = price;
        this.imageURL = imageURL;
        Quality = quality;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
