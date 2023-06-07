package com.maidanhdung.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.maidanhdung.ecommerce.R;
import com.maidanhdung.ecommerce.activities.ProductDetailActivity;
import com.maidanhdung.ecommerce.models.Products;

import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Products> products;

    public MyAdapter(Context context, ArrayList<Products> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .load(products.get(position).getImageProduct())
                .into(holder.imageView);
        holder.productname.setText(products.get(position).getProductName());
        holder.price.setText("Giá: "+products.get(position).getPrice()+" VNĐ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("ImageURL",products.get(holder.getAdapterPosition()).getImageProduct());
                intent.putExtra("ProductName",products.get(holder.getAdapterPosition()).getProductName());
                intent.putExtra("Price",products.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("description",products.get(holder.getAdapterPosition()).getDescription());
                //intent.putExtra("all",products.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (products != null) {
            return products.size();
        } else {
            return 0; // hoặc giá trị tùy ý nếu không có danh sách
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView productname, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgProduct);
            productname = itemView.findViewById(R.id.txtProductName);
            price = itemView.findViewById(R.id.txtPrice);
        }
    }
}
