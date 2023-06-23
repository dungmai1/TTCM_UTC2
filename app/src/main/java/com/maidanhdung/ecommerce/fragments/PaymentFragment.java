package com.maidanhdung.ecommerce.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.Home;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.activities.SignIn;
import com.maidanhdung.ecommerce.adapters.CartAdapter;
import com.maidanhdung.ecommerce.adapters.MyAdapter;
import com.maidanhdung.ecommerce.adapters.PaymentAdapter;
import com.maidanhdung.ecommerce.databinding.FragmentCartBinding;
import com.maidanhdung.ecommerce.databinding.FragmentPaymentBinding;
import com.maidanhdung.ecommerce.models.Address;
import com.maidanhdung.ecommerce.models.Cart;
import com.maidanhdung.ecommerce.models.Order;
import com.maidanhdung.ecommerce.models.Products;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    private PaymentAdapter paymentAdapter;
    DatabaseReference databaseReference,database;
    public static String strtext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Demo SDK";
    private String merchantCode = "SCB01";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";

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
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT); // AppMoMoLib.ENVIRONMENT.PRODUCTION
        loaddata();
        EventClickAddressPayment();
        EventClickPaymentDone();
        getdataYourAddress();
        return view;
    }
    private void getdataYourAddress() {
        getParentFragmentManager().setFragmentResultListener("data", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                strtext = result.getString("address");
                if(strtext!=null){
                    binding.txtAddressPayment.setText(strtext);
                }else{
                    binding.txtAddressPayment.setText("+ Add Address");
                }
            }
        });
    }
    private void EventClickPaymentDone() {
        binding.btnPaymentDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.txtAddressPayment.getText().toString().equals("+ Add Address")){
                    Toast.makeText(getContext(),"Please add a shipping address",Toast.LENGTH_LONG).show();
                }else if(binding.radioGroupPayment.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getContext(),"Select a payment method",Toast.LENGTH_LONG).show();
                }else{
                    if(binding.RadioBTNPaymentonDelivery.isChecked()){
                        Calendar calendar = Calendar.getInstance();
                        Date currentTime = calendar.getTime();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        String formattedTime = dateFormat.format(currentTime);
                        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(SignIn.phone));
                        database = FirebaseDatabase.getInstance().getReference("Order").child(String.valueOf(SignIn.phone));
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String productname = dataSnapshot.child("productName").getValue(String.class);
                                    int price = dataSnapshot.child("price").getValue(int.class);
                                    int quality = dataSnapshot.child("quality").getValue(int.class);
                                    String ImageProduct = dataSnapshot.child("imageURL").getValue(String.class);
                                    String ID = databaseReference.push().getKey();
                                    Order order = new Order(productname,"Processing",formattedTime,strtext,ImageProduct,price,quality);
                                    database.child(ID).setValue(order);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(),"Cancel",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        requestPayment();
                    }
                    databaseReference.removeValue();
                    getFragmentManager().popBackStack();
                    showDialog();
                }
            }
        });
    }

    private void EventClickAddressPayment() {
        binding.txtAddressPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YourAddressFragment yourAddressFragment = new YourAddressFragment();
                ((Home) requireActivity()).replaceFragment(yourAddressFragment);
            }
        });
    }

    private void loaddata() {
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
    }
    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", 0); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());
        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);
    }
    //Get token callback from MoMo app an submit to server side
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }
                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                } else {
                    //TOKEN FAIL
                }
            } else {
            }
        } else {
        }
}
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Order Success");
        AlertDialog dialog = builder.create();
        dialog.show();
        // Delay 2 giây
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 2000);
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