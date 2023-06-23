package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.AddressAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentEditAddressBinding;
import com.maidanhdung.ecommerce.databinding.FragmentYourAddressBinding;
import com.maidanhdung.ecommerce.models.Address;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditAddressFragment extends Fragment {

    FragmentEditAddressBinding binding;
    DatabaseReference databaseReference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AddressAdapter addressAdapter;
    String name,province,district,subdistrict,streetaddress;
    int phone;
    public EditAddressFragment(String name,String province,String district,String subdistrict,String streetaddress,int phone) {
        this.name = name;
        this.province = province;
        this.district = district;
        this.subdistrict = subdistrict;
        this.streetaddress = streetaddress;
        this.phone = phone;
    }
    public EditAddressFragment() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditAddressFragment newInstance(String param1, String param2) {
        EditAddressFragment fragment = new EditAddressFragment();
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
        binding = FragmentEditAddressBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.edtNameEdit.setText(name);
        binding.edtProvinceEdit.setText(province);
        binding.edtDistrictEdit.setText(district);
        binding.edtSubDistrictEdit.setText(subdistrict);
        binding.edtStreetAddressEdit.setText(streetaddress);
        binding.edtPhoneEdit.setText(String.valueOf(phone));
        EvenClickDeleteAddress();
        return view;
    }

    private void EvenClickDeleteAddress() {
        binding.btnDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Address").child(String.valueOf(SignIn.phone));
                databaseReference.orderByChild("")
            }
        });
    }
}