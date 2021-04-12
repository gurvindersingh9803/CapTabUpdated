package com.example.captab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<com.example.captab.OrderDetailsAdapter.OrderDetailsViewHolder> {

    List<OrderDetailsModal> orderDetailsList;
    Context mCtx;
    FragmentManager fragmentManager;



    public OrderDetailsAdapter(@NonNull Context mCtx, List<OrderDetailsModal> orderDetailsList) {
        super();
        this.orderDetailsList = orderDetailsList;
        this.mCtx = mCtx;

    }

    @NonNull
    @Override
    public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_order_details,parent,false);
        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int position) {

        final OrderDetailsModal orderDetailsModal;

        orderDetailsModal = orderDetailsList.get(position);

            holder.indicator.setImageResource(R.drawable.ic_baseline_fiber_manual_record_24);
            holder.date.setText("Ordered on : " + orderDetailsModal.getDate());
            holder.orderNumber.setText("Order number : " +orderDetailsModal.getOrderNumber());
            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(mCtx,TrackOrderStatus.class);
                    intent.putExtra("order_status", orderDetailsModal.getOrderstatus());

                    mCtx.startActivity(intent);
                    Toast.makeText(mCtx,orderDetailsModal.getOrderstatus(),Toast.LENGTH_LONG).show();
                }
            });

    }

    @Override
    public int getItemCount() {
        return orderDetailsList.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumber,date;
        ImageView  indicator;
        LinearLayout linearLayout;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.liner);
            orderNumber = itemView.findViewById(R.id.orderNumber);
            date = itemView.findViewById(R.id.date);
            indicator = itemView.findViewById(R.id.order_status_indicator);


        }
    }
}
