package com.example.captab;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    com.example.captab.OrderDetails currentOrderDetails;
    OrderDetailsAdapter orderDetailsAdapter;
    List<OrderDetailsModal> orderDetailsList;
    DatabaseReference databaseReferenceForOrder;
    FragmentManager fragmentManager;
    ValueEventListener mDBListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_fragment);
        recyclerView = findViewById(R.id.OrderDetailsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderDetailsList = new ArrayList<>();
        orderDetailsAdapter = new OrderDetailsAdapter(OrderDetails.this, orderDetailsList);
        recyclerView.setAdapter(orderDetailsAdapter);

//        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
       // final String curretntuserDecoded = currentUser.replace(".", "_");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String phoneNumber = user.getPhoneNumber();
        databaseReferenceForOrder = FirebaseDatabase.getInstance().getReference("orders").child(phoneNumber);

        mDBListener = databaseReferenceForOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //orderDetailsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    OrderDetailsModal upload = new OrderDetailsModal();
                    String uploadId = String.valueOf(postSnapshot.getKey());
                    upload = postSnapshot.getValue(OrderDetailsModal.class);

                    Log.i("gggggg", uploadId);
                    for (DataSnapshot postSnapshot1 : postSnapshot.getChildren()) {

                        if (postSnapshot1.exists()) {

                            upload.setOrderNumber(uploadId);

                        }

                    }

                    orderDetailsList.add(0, upload);
                    orderDetailsAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OrderDetails.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });



    }
}