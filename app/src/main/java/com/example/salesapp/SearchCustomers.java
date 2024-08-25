package com.example.salesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchCustomers extends AppCompatActivity {

    private SearchView search;
    private RecyclerView recyclerView;
    private String firebaseUrl = "https://salesapp-6c1e0-default-rtdb.asia-southeast1.firebasedatabase.app/";
    DatabaseReference databaseReference;
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customers);

        search = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.customerView);
        search.clearFocus();


        adapter = new SearchAdapter(getApplicationContext(),customerArrayList);
        recyclerView.setAdapter(adapter);


        databaseReference = FirebaseDatabase.getInstance(firebaseUrl).getReference();
        databaseReference.child("Customers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String cusCode = ds.child("cusCode").getValue(String.class);
                    String cusName = ds.child("cusName").getValue(String.class);
                    customerArrayList.add(new Customer(cusCode,cusName));
                }
                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filteredList(s);
                return true;
            }
        });

    }

    private void filteredList(String s) {
        ArrayList<Customer> filteredList = new ArrayList<>();
        for(Customer item:customerArrayList){
            if(item.getCusName().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }


}