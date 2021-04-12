package com.example.captab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchMedicines extends Fragment {

    private SearchMedicinesViewModel mViewModel;
    private RecyclerView mRecyclerView;
    ArrayList<MedicinesModalClass> medicinesModalClassList;
    DatabaseReference databaseReference;
    private ValueEventListener mDBListener;
    MedicinesModalClass medicinesModalClass;
    SearchView searchView;



    MedicinesAdapter medicinesAdapter;

    public static SearchMedicines newInstance() {
        return new SearchMedicines();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.search_medicines_fragment, container, false);

        ViewGroup vg = v.findViewById(R.id.l);
        vg.invalidate();

        searchView = (SearchView) v.findViewById(R.id.search);


        mRecyclerView = v.findViewById(R.id.recyclerview);
        //sv = (SearchView) findViewById(R.id.search_view);

        //FirebaseUser currentUser = auth.getCurrentUser();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        medicinesModalClassList = new ArrayList<>();

        //mRecyclerView.setAdapter(medicinesAdapter);

        medicinesAdapter = new MedicinesAdapter(getActivity(), (ArrayList<MedicinesModalClass>) medicinesModalClassList);
        mRecyclerView.setAdapter(medicinesAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);

                if(medicinesAdapter != null){

                    medicinesAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("medicines");
        //Query queryRef = mDatabaseRef.orderByChild("sellerId").equalTo(currentUser.getEmail());


        mDBListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                medicinesModalClassList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MedicinesModalClass upload = postSnapshot.getValue(MedicinesModalClass.class);
                    //assert upload != null;
                    upload.setKey(postSnapshot.getKey());
                    Log.i("TAAAAAAAAAAAAAAAGG", upload.getKey());
                    medicinesModalClassList.add(0, upload);
                    medicinesAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchMedicinesViewModel.class);
        // TODO: Use the ViewModel
    }





    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}