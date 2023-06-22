package com.maidanhdung.ecommerce.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.AddressAdapter;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.databinding.FragmentYourAddressBinding;
import com.maidanhdung.ecommerce.models.Address;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourAddressFragment extends Fragment {
    FragmentYourAddressBinding binding;
    private ArrayList<Address> addressArrayList;
    private AddressAdapter addressAdapter;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public YourAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YourAddressFragment newInstance(String param1, String param2) {
        YourAddressFragment fragment = new YourAddressFragment();
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
        //return inflater.inflate(R.layout.fragment_your_address, container, false);
        binding = FragmentYourAddressBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        EventClickAddnewAddress();
        loaddata();
        EventClickSave();

        return view;
    }

    private void EventClickSave() {
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addressAdapter.getItemCount() == 0){
                    Toast.makeText(getContext(),"No Address Yet",Toast.LENGTH_SHORT).show();
                }
                else if(addressAdapter.getSelectedItems() == null){
                    Toast.makeText(getContext(),"Choose the delivery address",Toast.LENGTH_SHORT).show();
                }
                else{
                    sendataAddress();
                }
            }
        });
    }
    private void sendataAddress(){
        Bundle bundle = new Bundle();
        bundle.putString("address", addressAdapter.getSelectedItems());
        getParentFragmentManager().setFragmentResult("data",bundle);
        getFragmentManager().popBackStack();
    }
    private void loaddata() {
        binding.recyclerviewAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("Address").child(String.valueOf(SignIn.phone));
        addressArrayList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getActivity(), addressArrayList);
        binding.recyclerviewAddress.setAdapter(addressAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Address address = dataSnapshot.getValue(Address.class);
                    addressArrayList.add(address);
                }
                addressAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void EventClickAddnewAddress() {
        binding.btnAddnewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAdressFragment addAdressFragment = new AddAdressFragment();
                ((Home) requireActivity()).replaceFragment(addAdressFragment);
            }
        });
    }
}