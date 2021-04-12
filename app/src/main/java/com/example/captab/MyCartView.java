package com.example.captab;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCartView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    List<CartModalClass> cartlist;
    private DatabaseReference databaseReferenceForCart;
    private ValueEventListener mDBListener,mDBListeners;
    String numOfrecordsFromOrder,numOfrecordsFromCart;
    MyCartAdapter myCartAdapter;
    int totalPriceOfCartProducts = 0;
    Button placeorder;
    CartModalClass upload;
    int orderNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_view);

        mRecyclerView = findViewById(R.id.rv);
        placeorder = findViewById(R.id.placeOrder);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        cartlist = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(com.example.captab.MyCartView.this, cartlist);
        mRecyclerView.setAdapter(myCartAdapter);
        //final String curretntuserDecoded = currentUser.replace(".","_");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String phoneNumber = user.getPhoneNumber();
        databaseReferenceForCart = FirebaseDatabase.getInstance().getReference("cart/customers/"+ phoneNumber);

            mDBListener = databaseReferenceForCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //cartlist.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    CartModalClass upload = postSnapshot.getValue(CartModalClass.class);
                    //int price = upload.getTotalPrice();
                  // Log.i("fffff", String.valueOf(upload)) ;
                    //Log.i("fffff",String.valueOf(upload.getTotalPrice())) ;
                   // upload.getMedicineName();
                    //upload.getTotalPrice();
                    //upload.getPrice();
                    //upload.getQuantity();
                    //totalPriceOfCartProducts = totalPriceOfCartProducts + price;

                    Log.d(getClass().getName(), "value = " + totalPriceOfCartProducts);

                        cartlist.add(0,upload);


                }
                myCartAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(com.example.captab.MyCartView.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference databaseReferenceForOrder = FirebaseDatabase.getInstance().getReference("orders").child(phoneNumber).child(String.valueOf(System.currentTimeMillis()));

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        databaseReferenceForOrder.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isComplete()) {
                                    //Log.d("OKKKKKKKKKKKKKKKKKKKKKK", dataSnapshot.getValue().toString());
                                    databaseReferenceForCart.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Map map = new HashMap();
                                                map.put("date",getDateTime());
                                                map.put("orderStatus","OrderPlaced");
                                                databaseReferenceForOrder.updateChildren(map);
                                            }
                                        }
                                    });
                                } else {
                                    Log.d("nothinggggggggg", "Copy failed!");
                                }
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                databaseReferenceForCart.addListenerForSingleValueEvent(valueEventListener);

        }


        });
    }
    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }




}