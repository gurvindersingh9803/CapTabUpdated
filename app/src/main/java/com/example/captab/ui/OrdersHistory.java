package com.example.captab.ui;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.captab.OrderDetails;
import com.example.captab.OrderDetailsAdapter;
import com.example.captab.OrderDetailsModal;
import com.example.captab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrdersHistory extends Fragment {

    RecyclerView recyclerView;
    com.example.captab.OrderDetails currentOrderDetails;
    OrderDetailsAdapter orderDetailsAdapter;
    List<OrderDetailsModal> orderDetailsList;
    FragmentManager fragmentManager;

    DatabaseReference databaseReferenceForOrder;
    ValueEventListener mDBListener;
    private OrderDetails mViewModel;

    public static OrdersHistory newInstance() {
        return new OrdersHistory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.orders_fragment, container, false);
        recyclerView = view.findViewById(R.id.OrderDetailsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderDetailsList = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(getActivity(), orderDetailsList);
        recyclerView.setAdapter(orderDetailsAdapter);

        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String curretntuserDecoded = currentUser.replace(".","_");
        databaseReferenceForOrder = FirebaseDatabase.getInstance().getReference("orders").child(curretntuserDecoded);


        mDBListener = databaseReferenceForOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //orderDetailsList.clear();
                OrderDetailsModal upload = new OrderDetailsModal();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String uploadId = String.valueOf(postSnapshot.getKey());

                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {

                        if(postSnapshot1.exists()) {

                            upload = postSnapshot1.getValue(OrderDetailsModal.class);

                            upload.setOrderNumber(uploadId);
                            Log.i("gggggggggggggg", String.valueOf(postSnapshot));
                        }

                    }

                    orderDetailsList.add(0, upload);
                    orderDetailsAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }


}