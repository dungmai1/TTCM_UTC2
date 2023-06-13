package com.maidanhdung.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.fragments.CartFragment;
import com.maidanhdung.ecommerce.models.Address;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Address> addressArrayList;
    int SelectedPosition = -1;
    private String selectedAddress;
    private boolean[] checkedPositions;

    public AddressAdapter(){
    }
    public AddressAdapter(Context context, ArrayList<Address> addressArrayList) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        TextView txtAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            txtAddress = itemView.findViewById(R.id.txtAddress);
        }
    }
}
