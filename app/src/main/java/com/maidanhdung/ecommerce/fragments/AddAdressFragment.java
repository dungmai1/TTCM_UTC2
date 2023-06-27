package com.maidanhdung.ecommerce.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.api.ApiService;
import com.maidanhdung.ecommerce.databinding.FragmentAddAdressBinding;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.models.Address;
import com.maidanhdung.ecommerce.models.District;
import com.maidanhdung.ecommerce.models.Province;
import com.maidanhdung.ecommerce.models.ResultWrapper;
import com.maidanhdung.ecommerce.models.ResultWrapper1;
import com.maidanhdung.ecommerce.models.ResultWrapper2;
import com.maidanhdung.ecommerce.models.Ward;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    String selectedProvinceId;
    String selectedDistrictId;
    String selectedDistrict;
    String selectedSubDistrict;
    String selectedProvince;


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
        loadProvince();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDistrict = binding.spinnerDistrict.getSelectedItem().toString();
                selectedProvince = binding.spinnerProvince.getSelectedItem().toString();
                selectedSubDistrict =  binding.spinnerSubdistrict.getSelectedItem().toString();
                String name = binding.editTextName.getText().toString();
                String streetaddress = binding.editTextAddress.getText().toString();
                String phoneString = binding.editTextPhone.getText().toString();
                int phone =0;
                if (name.isEmpty()) {
                    binding.editTextName.setError("Please enter a name");
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
                if(!name.isEmpty()||!streetaddress.isEmpty()||!phoneString.isEmpty()){
                    databaseReference = FirebaseDatabase.getInstance().getReference("Address").child(String.valueOf(SignIn.phone));
                    String ID = databaseReference.push().getKey();
                    Address address = new Address(name,selectedProvince,selectedSubDistrict,selectedDistrict,streetaddress,phone);
                    databaseReference.child(ID).setValue(address);
                    Toast.makeText(getContext(),"Add new address succesfully",Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStack();
                }
            }
        });
        return view;
    }
    private void loadWard(String DistrictID){
        ApiService.apiservice.loadWard(DistrictID).enqueue(new Callback<ResultWrapper2>() {
            @Override
            public void onResponse(Call<ResultWrapper2> call, Response<ResultWrapper2> response) {
                ResultWrapper2 resultWrapper2 = response.body();
                if (resultWrapper2 != null && resultWrapper2.results != null) {
                    Ward[] wards = resultWrapper2.results;
                    if (wards.length > 0) {
                        ArrayList<String> arrayList2 = new ArrayList<>();
                        for (Ward ward : wards) {
                            arrayList2.add(ward.getWard_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList2);
                        binding.spinnerSubdistrict.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No districts available", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext(), "Succes" +
                            "s ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Empty district response", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResultWrapper2> call, Throwable t) {

            }
        });
    }
    private void loadProvince() {
        ApiService.apiservice.loadProvince().enqueue(new Callback<ResultWrapper>() {
            @Override
            public void onResponse(Call<ResultWrapper> call, Response<ResultWrapper> response) {
                ResultWrapper resultWrapper = response.body();
                Province[] provinces = resultWrapper.results;
                if (provinces != null && provinces.length > 0) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (Province province : provinces) {
                        arrayList.add(province.getProvince_name());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList);
                    binding.spinnerProvince.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    binding.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedProvinceName = arrayList.get(position);
                            for (Province province : provinces) {
                                if (province.getProvince_name().equals(selectedProvinceName)) {
                                    selectedProvinceId = province.getProvince_id();
                                    // Gọi API loadDistrict khi chọn tỉnh
                                    loadDistrict(selectedProvinceId);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    Toast.makeText(getContext(), "Call API Success", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Empty response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultWrapper> call, Throwable t) {
                Toast.makeText(getContext(), "Call API FAIL", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loadDistrict(String provinceId) {
        ApiService.apiservice.loadDistrict(provinceId).enqueue(new Callback<ResultWrapper1>() {
            @Override
            public void onResponse(Call<ResultWrapper1> call, Response<ResultWrapper1> response) {
                ResultWrapper1 districtResultWrapper = response.body();
                if (districtResultWrapper != null && districtResultWrapper.results != null) {
                    District[] districts = districtResultWrapper.results;
                    if (districts.length > 0) {
                        ArrayList<String> arrayList1 = new ArrayList<>();
                        for (District district : districts) {
                            arrayList1.add(district.getDistrict_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, arrayList1);
                        binding.spinnerDistrict.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        binding.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String selectedDistrictName = arrayList1.get(position);
                                for (District district : districts) {
                                    if (district.getDistrict_name().equals(selectedDistrictName)) {
                                        selectedDistrictId = district.getDistrict_id();

                                        loadWard(selectedDistrictId);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "No districts available", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext(), "Seccess ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Empty district response", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResultWrapper1> call, Throwable t) {
                Toast.makeText(getContext(), "Call API FAIL", Toast.LENGTH_LONG).show();
            }
        });
    }
}