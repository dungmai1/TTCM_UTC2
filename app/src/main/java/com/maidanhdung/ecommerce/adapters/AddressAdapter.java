package com.maidanhdung.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.fragments.EditAddressFragment;
import com.maidanhdung.ecommerce.models.Address;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Address> addressArrayList;
    int SelectedPosition = -1;
    private String selectedAddress;
    IAddressRecyclerview mListener;
    public AddressAdapter(){
    }
    public AddressAdapter(Context context, ArrayList<Address> addressArrayList, IAddressRecyclerview mListener) {
        this.mListener = mListener;
        this.context = context;
        this.addressArrayList = addressArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtAddress.setText(addressArrayList.get(position).getName() + "," +
                addressArrayList.get(position).getProvince() + "," +
                addressArrayList.get(position).getSubDistrict() + "," +
                addressArrayList.get(position).getProvince() + "," +
                addressArrayList.get(position).getStreetAddress());
        holder.radioButton.setChecked(position == SelectedPosition);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedPosition = holder.getAdapterPosition();
                selectedAddress = holder.txtAddress.getText().toString();
                notifyDataSetChanged();
            }
        });
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new EditAddressFragment(
                                addressArrayList.get(position).getName(),
                                addressArrayList.get(position).getProvince(),
                                addressArrayList.get(position).getDistrict(),
                                addressArrayList.get(position).getSubDistrict(),
                                addressArrayList.get(position).getStreetAddress(),
                                addressArrayList.get(position).getPhone())).addToBackStack(null).commit();
                //mListener.gotoEditAddress();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressArrayList.size();
    }
    public String getSelectedItems() {
        return selectedAddress;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton radioButton;
        TextView txtAddress,txtEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            }
    }
    public interface IAddressRecyclerview{
        void gotoEditAddress( );
    }
}
