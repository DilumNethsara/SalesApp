package com.example.salesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder>{

    Context context;
    ArrayList<Customer> customerArrayList;
   public SearchAdapter(Context context, ArrayList<Customer> customerArrayList){
       this.context = context;
       this.customerArrayList = customerArrayList;
   }

    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_customer,parent,false);
        return new SearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MyViewHolder holder, int position) {
        holder.txtCusCode.setText(customerArrayList.get(position).getCusCode());
        holder.txtCusName.setText(customerArrayList.get(position).getCusName());
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public void filterList(ArrayList<Customer> filteredList) {
       customerArrayList = filteredList;
       notifyDataSetChanged();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtCusCode, txtCusName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCusCode = itemView.findViewById(R.id.txt_cusCode);
            txtCusName = itemView.findViewById(R.id.txt_cusName);
        }
    }
}
