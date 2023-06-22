package com.maidanhdung.ecommerce.fragments;

import static java.sql.DriverManager.println;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.adapters.MyOrderAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentHomeBinding;
import com.maidanhdung.ecommerce.databinding.FragmentMyOrderBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Order;
import com.maidanhdung.ecommerce.models.Products;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyOrderFragment extends Fragment {
    FragmentMyOrderBinding binding;
    DatabaseReference databaseReference;
    private MyOrderAdapter myOrderAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
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
        binding = FragmentMyOrderBinding.inflate(inflater, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        View view = binding.getRoot();
        //loaddata();
        binding.recyclerviewMyOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        EventClickAll();
        EventClickProcessing();
        EventClickShipping();
        EventClickDelivered();
        EventClickCanceled();
        return view;
    }
    private void EventClickCanceled() {
        binding.btnCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                FirebaseRecyclerOptions<Order> options =
                        new FirebaseRecyclerOptions.Builder<Order>()
                                .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("Order").child(String.valueOf(SignIn.phone))
                                        .orderByChild("status").equalTo("Canceled"), Order.class)
                                .build();
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(options);
                binding.recyclerviewMyOrder.setAdapter(myOrderAdapter);
                binding.btnCanceled.setTextColor(Color.RED);
                myOrderAdapter.startListening();
            }
        });
    }
    private void EventClickDelivered() {

        binding.btnDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                FirebaseRecyclerOptions<Order> options =
                        new FirebaseRecyclerOptions.Builder<Order>()
                                .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("Order").child(String.valueOf(SignIn.phone))
                                        .orderByChild("status").equalTo("Delivered"), Order.class)
                                .build();
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(options);
                binding.recyclerviewMyOrder.setAdapter(myOrderAdapter);
                binding.btnDelivered.setTextColor(Color.RED);
                myOrderAdapter.startListening();
            }
        });
    }

    private void EventClickShipping() {
        binding.btnShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                FirebaseRecyclerOptions<Order> options =
                        new FirebaseRecyclerOptions.Builder<Order>()
                                .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("Order").child(String.valueOf(SignIn.phone))
                                        .orderByChild("status").equalTo("Shipping"), Order.class)
                                .build();
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(options);
                binding.recyclerviewMyOrder.setAdapter(myOrderAdapter);
                binding.btnShipping.setTextColor(Color.RED);

                myOrderAdapter.startListening();
            }
        });
    }

    private void EventClickProcessing() {
        binding.btnProcessing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor();
                FirebaseRecyclerOptions<Order> options =
                        new FirebaseRecyclerOptions.Builder<Order>()
                                .setQuery(FirebaseDatabase.getInstance().getReference()
                                        .child("Order").child(String.valueOf(SignIn.phone))
                                        .orderByChild("status").equalTo("Processing"), Order.class)
                                .build();
                MyOrderAdapter myOrderAdapter = new MyOrderAdapter(options);
                binding.recyclerviewMyOrder.setAdapter(myOrderAdapter);
                binding.btnProcessing.setTextColor(Color.RED);

                myOrderAdapter.startListening();
            }
        });
    }

    private void EventClickAll() {
        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();
                changeColor();
                binding.btnAll.setTextColor(Color.RED);
                // Start listening after setting the adapter
                myOrderAdapter.startListening();
            }
        });
    }
    private void loaddata() {
        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").child(String.valueOf(SignIn.phone)), Order.class)
                        .build();
        myOrderAdapter = new MyOrderAdapter(options);
        binding.recyclerviewMyOrder.setAdapter(myOrderAdapter);
    }
    private void changeColor(){
        binding.btnAll.setTextColor(Color.parseColor("#000000"));
        binding.btnProcessing.setTextColor(Color.parseColor("#000000"));
        binding.btnShipping.setTextColor(Color.parseColor("#000000"));
        binding.btnDelivered.setTextColor(Color.parseColor("#000000"));
        binding.btnCanceled.setTextColor(Color.parseColor("#000000"));
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if the adapter is not null before calling startListening()
        if (myOrderAdapter != null) {
            myOrderAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        // Check if the adapter is not null before calling stopListening()
        if (myOrderAdapter != null) {
            myOrderAdapter.stopListening();
        }
    }
}
