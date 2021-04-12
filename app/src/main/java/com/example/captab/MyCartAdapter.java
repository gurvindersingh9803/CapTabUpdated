package com.example.captab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MycartViewHolder> {
    Context mCtx;
    List<CartModalClass> cartProductslist;

    public MyCartAdapter(@NonNull Context context, List<CartModalClass> listofcart)
    {
        this.mCtx = context;
        this.cartProductslist = listofcart;
    }

    @NonNull
    @Override
    public MycartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.single_cart_product, parent, false);
        return new MycartViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull MycartViewHolder holder, int position) {

        final CartModalClass currentCartDetails;

        currentCartDetails = cartProductslist.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx,currentCartDetails.getMedicineName(),Toast.LENGTH_LONG).show();
            }
        });
        if(currentCartDetails.getMedicineName() != null) {
            holder.medName.setText(currentCartDetails.getMedicineName());
            holder.price.setText("Rs."+currentCartDetails.getTotalPrice());
            holder.quantity.setText("Qty:"+currentCartDetails.getQuantity() +" x "+ currentCartDetails.getOrderType());

        }else{
            holder.medName.setText("Nothing");
        }
    }

    @Override
    public int getItemCount() {
        return cartProductslist.size();
    }


    public class MycartViewHolder extends RecyclerView.ViewHolder {

        TextView medName,quantity,price;
        LinearLayout linearLayout;

        public MycartViewHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.CartmedicineName);
            linearLayout = itemView.findViewById(R.id.li);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.CartmedicinePrice);


        }
    }
}
