package com.maidanhdung.ecommerce.models;

public class Order {
    String Product, Status, OrderPlace, Address, ImageProduct;
    int Price,Quality;
    public Order() {
    }
    public Order(String product, String status, String orderPlace, String address, String imageProduct, int price, int quality) {
        Product = product;
        Status = status;
        OrderPlace = orderPlace;
        Address = address;
        ImageProduct = imageProduct;
        Price = price;
        Quality = quality;
    }
    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public int getQuality() {
        return Quality;
    }

    public void setQuality(int quality) {
        Quality = quality;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderPlace() {
        return OrderPlace;
    }

    public void setOrderPlace(String orderPlace) {
        OrderPlace = orderPlace;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
