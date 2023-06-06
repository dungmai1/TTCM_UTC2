package com.maidanhdung.ecommerce.models;

import java.io.Serializable;

public class Products implements Serializable {
    String ProductName, ImageProduct, Price, Description;
    public Products() {
    }

    public Products(String productName, String imageProduct, String price, String description ) {
        ProductName = productName;
        ImageProduct = imageProduct;
        Price = price;
        Description = description;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
