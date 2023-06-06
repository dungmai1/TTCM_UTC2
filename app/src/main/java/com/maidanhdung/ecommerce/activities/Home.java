package com.maidanhdung.ecommerce.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.storage.StorageReference;
import com.maidanhdung.ecommerce.fragments.CartFragment;
import com.maidanhdung.ecommerce.fragments.HomeFragment;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.fragments.OrderHistoryFragment;
import com.maidanhdung.ecommerce.fragments.ProfileFragment;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.ActivityHomeBinding;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    private RecyclerView recyclerView;

    private ArrayList<Products> imageproducts;
    private MyAdapter myAdapter;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        recyclerView = findViewById(R.id.recycleview);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(myAdapter);
//
//        imageproducts = new ArrayList<>();
//
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("food");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Products products1 = new Products();
//                    products1.setProductName(snapshot.child("ProductName").getValue().toString());
//                    products1.setPrice(snapshot.child("Price").getValue().toString());
//                    products1.setImageProduct(snapshot.child("ImageProduct").getValue().toString());
//                    imageproducts.add(products1);
//                }
//                myAdapter = new MyAdapter(getApplicationContext(),imageproducts);
//                recyclerView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Home.this,"Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });

        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.orderhistory:
                    replaceFragment(new OrderHistoryFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}