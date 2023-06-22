package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.databinding.FragmentHomeBinding;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ObjIntConsumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private ArrayList<Products> imageproducts;
    private MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        loaddata();
        searchProducts();
        return view;
    }
    private void searchProducts() {
        binding.IDSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                String searchText = binding.IDSearchView.getQuery().toString();
                filterList(searchText);
                return true;
            }
        });
    }
    private void loaddata() {
        databaseReference = FirebaseDatabase.getInstance().getReference("food");
        binding.recyclerview.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager);
        imageproducts = new ArrayList<>();
        myAdapter = new MyAdapter(getActivity(), imageproducts);
        binding.recyclerview.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageproducts.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Products products = dataSnapshot.getValue(Products.class);
                    DataSnapshot detailsSnapshot = dataSnapshot.child("ProductDetail");
                    String ImageDetail1 = detailsSnapshot.child("ImageDetail1").getValue(String.class);
                    String ImageDetail2 = detailsSnapshot.child("ImageDetail2").getValue(String.class);
                    String ImageDetail3 = detailsSnapshot.child("ImageDetail3").getValue(String.class);
                    String ImageDetail4 = detailsSnapshot.child("ImageDetail4").getValue(String.class);
                    // Gán dữ liệu từ nhánh "details" vào đối tượng Products
                    String description = detailsSnapshot.child("Description").getValue(String.class);
                    products.setDescription(description);
                    products.setImageDetail1(ImageDetail1);
                    products.setImageDetail2(ImageDetail2);
                    products.setImageDetail3(ImageDetail3);
                    products.setImageDetail4(ImageDetail4);
                    imageproducts.add(products);
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void filterList(String searchText){
        ArrayList<Products> filteredList = new ArrayList<>();

        for (Products product : imageproducts) {
            // Kiểm tra nếu tên sản phẩm hoặc bất kỳ thuộc tính khác nào bạn muốn tìm kiếm chứa searchText
            if (product.getProductName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(product);
            }
        }
        myAdapter.filterList(filteredList);
    }
}