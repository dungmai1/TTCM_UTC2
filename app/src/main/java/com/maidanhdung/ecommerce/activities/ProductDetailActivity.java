package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityProductDetailBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Products;

import java.io.Serializable;

public class ProductDetailActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    ActivityProductDetailBinding binding;
    Products products = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ImageView imageView = binding.imageView;
        // Nhận URL hình ảnh từ Intent
        String ImageURL = getIntent().getStringExtra("ImageURL");
        String ProductName = getIntent().getStringExtra("ProductName");
        String Price = getIntent().getStringExtra("Price");
        String description = getIntent().getStringExtra("description");
        binding.txtProductNameDetail.setText(ProductName);
        binding.txtPrice.setText(Price+" VNĐ");
        binding.txtDesciption.setText(description);
        String Quality =binding.Quality.getText().toString();
        // Sử dụng thư viện Glide để tải và hiển thị hình ảnh từ URL
        Glide.with(this)
                .load(ImageURL)
                .into(imageView);
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
                String ID = databaseReference.push().getKey();
                Cart cart = new Cart(ProductName,Price,ImageURL,Quality);
                databaseReference.child(ID).setValue(cart);
                Toast.makeText(ProductDetailActivity.this,"Add to cart success",Toast.LENGTH_LONG).show();
            }
        });
        binding.BtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(binding.Quality.getText().toString());
                //Toast.makeText(ProductDetailActivity.this,count,Toast.LENGTH_LONG).show();
                binding.Quality.setText(String.valueOf(count+1));
            }
        });
        binding.BtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(binding.Quality.getText().toString());
                if(count!=1){
                    binding.Quality.setText(String.valueOf(count-1));
                }
            }
        });
        //Load dữ liêu sử dụng Serializable
//        Intent intent = getIntent();
//        if (intent != null) {
//            if (intent.hasExtra("all")) {
//                // Tiếp tục xử lý dữ liệu
//            } else {
//                // Không có dữ liệu "all" được truyền
//            }
//        }
//        if (intent.hasExtra("all")) {
//            Serializable obj = intent.getSerializableExtra("all");
//            if (obj instanceof Products) {
//                Products allData = (Products) obj;
//                String productName = allData.getProductName();
//                String price = allData.getPrice();
//                String description = allData.getDescription();
//                binding.txtProductNameDetail.setText(productName);
//                binding.txtPrice.setText(price);
//                binding.txtDesciption.setText(description);
//            } else {
//                // Đối tượng Serializable không phải là một đối tượng của lớp Product
//            }
//        } else {
//            // Không có dữ liệu "all" được truyền
//        }
    }
}