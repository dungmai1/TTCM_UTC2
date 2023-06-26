package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.databinding.FragmentAddAdressBinding;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.models.Address;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAdressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAdressFragment extends Fragment {
    FragmentAddAdressBinding binding;
    private DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAdressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAdressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAdressFragment newInstance(String param1, String param2) {
        AddAdressFragment fragment = new AddAdressFragment();
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
        //return inflater.inflate(R.layout.fragment_add_adress, container, false);
        binding = FragmentAddAdressBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextName.getText().toString();
                String province = binding.editTextProvince.getText().toString();
                String district = binding.editTextDistrict.getText().toString();
                String subdistrict = binding.editTextSubDistrict.getText().toString();
                String streetaddress = binding.editTextAddress.getText().toString();
                String phoneString = binding.editTextPhone.getText().toString();
                int phone =0;
                if (name.isEmpty()) {
                    binding.editTextName.setError("Please enter a name");
                }
                if (province.isEmpty()) {
                    binding.editTextProvince.setError("Please enter a province");
                }
                if (district.isEmpty()) {
                    binding.editTextDistrict.setError("Please enter a district");
                }
                if (subdistrict.isEmpty()) {
                    binding.editTextSubDistrict.setError("Please enter a subdistrict");
                }
                if (streetaddress.isEmpty()) {
                    binding.editTextAddress.setError("Please enter a street address");
                }
                if (phoneString.isEmpty()) {
                    // Phone number field is empty
                    binding.editTextPhone.setError("Please enter a phone number");
                    return;
                }
                try {
                    phone = Integer.parseInt(phoneString);
                } catch (NumberFormatException e) {
                    binding.editTextPhone.setError("Please enter a valid phone number");
                }
                if(!name.isEmpty()||!province.isEmpty()||!district.isEmpty()||!subdistrict.isEmpty()||!streetaddress.isEmpty()||!phoneString.isEmpty()){
                    databaseReference = FirebaseDatabase.getInstance().getReference("Address").child(String.valueOf(SignIn.phone));
                    String ID = databaseReference.push().getKey();
                    Address address = new Address(name,province,subdistrict,district,streetaddress,phone);
                    databaseReference.child(ID).setValue(address);
                    Toast.makeText(getContext(),"Add new address succesfully",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }
}