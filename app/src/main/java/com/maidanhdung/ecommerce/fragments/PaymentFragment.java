package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.adapters.PaymentAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.databinding.FragmentPaymentBinding;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    private PaymentAdapter paymentAdapter;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        //return inflater.inflate(R.layout.fragment_payment, container, false);
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //View rootView = inflater.inflate(R.layout.fragment_cart, container, false);
        binding.recyclerviewPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(SignIn.txtPhone), Cart.class)
                        .build();
        paymentAdapter = new PaymentAdapter(options);
        binding.recyclerviewPayment.setAdapter(paymentAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(SignIn.txtPhone);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int price = dataSnapshot.child("price").getValue(int.class);
                    int quality = dataSnapshot.child("quality").getValue(int.class);
                    total = total + ((price) * quality);
                }
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String totalFormat = decimalFormat.format(total);
                binding.TotalPayment.setText("Total: " + totalFormat+ " VNĐ");
                //Toast.makeText(getContext(),String.valueOf(total),Toast.LENGTH_LONG).show();
                paymentAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        binding.txtAddressPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YourAddressFragment yourAddressFragment = new YourAddressFragment();
                ((Home) requireActivity()).replaceFragment(yourAddressFragment);
            }
        });
        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String strtext = result.getString("address");
                if(strtext!=null){
                    binding.txtAddressPayment.setText(strtext);
                }else{
                    binding.txtAddressPayment.setText("No Address");
                }
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        paymentAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        paymentAdapter.stopListening();
    }
}