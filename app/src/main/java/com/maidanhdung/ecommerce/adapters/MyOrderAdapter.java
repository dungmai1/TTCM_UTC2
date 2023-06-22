package com.maidanhdung.ecommerce.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.models.Order;

import java.text.DecimalFormat;
import java.util.List;

public class MyOrderAdapter extends FirebaseRecyclerAdapter<Order,MyOrderAdapter.ViewHolder> {
    public MyOrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Order order) {
        Glide.with(holder.imageURL.getContext())
                .load(order.getImageProduct())
                .into(holder.imageURL);
        holder.productName.setText(order.getProduct());
        holder.Quality.setText("Quality: "+String.valueOf(order.getQuality()));
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String PriceFormat = decimalFormat.format(order.getPrice());
        holder.Price.setText(PriceFormat+" VNĐ");
        int quality = order.getQuality();
        int price = order.getPrice();
        int total = quality*price;
        String TotalFormat = decimalFormat.format(total);
        holder.Total.setText(TotalFormat+ " VNĐ");
        holder.Status.setText(order.getStatus());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myorder,parent,false);
        return  new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageURL;
        TextView productName, Quality, Price, Total,Status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageURL = itemView.findViewById(R.id.imgOrder);
            productName = itemView.findViewById(R.id.txtProductNameMyOrder);
            Quality = itemView.findViewById(R.id.txtQualityMyOrder);
            Price = itemView.findViewById(R.id.txtPriceMyOrder);
            Total = itemView.findViewById(R.id.txtTotalMyOrder);
            Status = itemView.findViewById(R.id.txtStatusOrder);
        }
    }
}
