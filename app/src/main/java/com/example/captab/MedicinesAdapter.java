package com.example.captab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MedicinesViewHolder> implements Filterable {
    private ArrayList<MedicinesModalClass> medicinesList = new ArrayList<>();
    private Context mCtx;
    String QuantityID = " ";
    Spinner QuantitySpinner;
    RadioGroup radioGroup;
    RadioButton radioButton;
    int selectedId;

    FirebaseAuth mAuth;
    private DatabaseReference ref;
    Map<String, Object> cartDetails;
   // ValueFilter valueFilter;
    private ArrayList<MedicinesModalClass> arrayListFiltered;

    String quantity,units;

    public MedicinesAdapter(Context mCtxs, ArrayList<MedicinesModalClass> medicinesLists) {


        mCtx = mCtxs;
        medicinesList = medicinesLists;
        arrayListFiltered = medicinesLists;

        //Log.i("wdfffffffffffffff", String.valueOf(arrayListFiltered.size()));


    }


    public MedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mCtx).inflate(R.layout.single_medicine_view, parent, false);
        return new MedicinesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesViewHolder holder, int position) {

        final MedicinesModalClass currentMedDetails;

        currentMedDetails = arrayListFiltered.get(position);

        if (currentMedDetails.getMedName() != "") {
            holder.medname.setText(currentMedDetails.getMedName());
            holder.addTocart.setOnClickListener(new View.OnClickListener() {
                                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                                    @Override
                                                    public void onClick(View view) {



                                                        final AlertDialog alertDialog = new AlertDialog.Builder(mCtx).create();
                                                        View dialogView;
                                                        if(currentMedDetails.getCategory().equals("Tablets")) {

                                                            LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
                                                            dialogView = layoutInflater.inflate(R.layout.spinner_dialog,null);
                                                            alertDialog.setIcon(R.mipmap.ic_launcher);
                                                            alertDialog.setView(dialogView);

                                                        }else{

                                                            LayoutInflater inflater = LayoutInflater.from(mCtx);
                                                            dialogView = inflater.inflate(R.layout.dialog_withot_tablets, null);
                                                            alertDialog.setView(dialogView);


                                                        }alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {


                                                                EditText text;
                                                                RadioButton radioButton = null;
                                                                if(currentMedDetails.getCategory().equals("Tablets")) {
                                                                    text = (EditText) alertDialog.findViewById(R.id.numOfUnits);
                                                                    String value = text.getText().toString();
                                                                    int finalValue = Integer.parseInt(value);
                                                                    RadioGroup radioGroup;
                                                                    
                                                                    radioGroup = (RadioGroup) alertDialog.findViewById(R.id.typeOfUnits);
                                                                    final int selectedId = radioGroup.getCheckedRadioButtonId();
                                                                    radioButton = (RadioButton) alertDialog.findViewById(selectedId);
                                                                    units = radioButton.getText().toString();

                                                                }else{

                                                                    text = (EditText) alertDialog.findViewById(R.id.numOfUnit);
                                                                    units = currentMedDetails.getCategory();

                                                                }

                                                                cartDetails = new HashMap<String, Object>();
                                                                cartDetails.put("medicineName", currentMedDetails.getMedName());
                                                                cartDetails.put("salt", currentMedDetails.getComposition());
                                                                cartDetails.put("price", "0");
                                                                cartDetails.put("totalPrice", "5");
                                                                cartDetails.put("quantity", text.getText().toString());
                                                                cartDetails.put("units", units);
                                                                cartDetails.put("orderCategory", units);


                                                                String email;
                                                                //email = FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "_");
                                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                                String phoneNumber = user.getPhoneNumber();


                                                                ref = FirebaseDatabase.getInstance().getReference("cart").child("customers").child(phoneNumber);
                                                                String uploadId = ref.push().getKey();
                                                                //String contextname = String.valueOf(mCtx);
                                                                //String path = String.valueOf(ref).substring(ref.getRoot().toString().length() - 1);

                                                                if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                                                                    //Toast.makeText(mCtx, currentMedDetails.getMedName(), Toast.LENGTH_LONG).show();


                                                                    if (cartDetails != null) {
                                                                        ref.child(uploadId).setValue(cartDetails)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                        if (task.isSuccessful()) {

                                                                                            Toast.makeText(mCtx, "Added to cart!", Toast.LENGTH_LONG).show();

                                                                                        }

                                                                                    }
                                                                                });


                                                                    }
                                                                } else {

                                                                    Toast.makeText(mCtx, "Please login first!", Toast.LENGTH_LONG).show();

                                                                }


                                                            }
                                                        });


                                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                alertDialog.dismiss();
                                                            }
                                                        });









                                                        alertDialog.show();





                                                    }
                                                }
            );
            //holder.medPrice.setText(  medicinesModalClass.getPrice());
            //holder.medSalt.setText(medicinesModalClass.getComposition());

        } else {

            holder.medname.setText("heyy");

        }
    }

    private String getDateTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    @Override
    public int getItemCount() {



        return arrayListFiltered.size();
    }



    public class MedicinesViewHolder extends RecyclerView.ViewHolder {


        TextView medname, medSalt, medPrice;

        TextView addTocart;

        public MedicinesViewHolder(@NonNull View itemView) {
            super(itemView);

            medname = itemView.findViewById(R.id.medicineName);
            addTocart = itemView.findViewById(R.id.addToCart);

        }


    }




    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    arrayListFiltered = medicinesList;
                } else {
                    List<MedicinesModalClass> filteredList = new ArrayList<>();
                    for (MedicinesModalClass row : medicinesList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMedName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    arrayListFiltered = (ArrayList<MedicinesModalClass>) filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arrayListFiltered = (ArrayList<MedicinesModalClass>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }





}