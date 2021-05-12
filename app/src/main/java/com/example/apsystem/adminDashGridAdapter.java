package com.example.apsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class adminDashGridAdapter extends RecyclerView.Adapter<adminDashGridAdapter.ViewHolder> {
    public List<adminDashGridItem> listItems;
    private Context context;


    public adminDashGridAdapter(List<adminDashGridItem> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v;
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_admin_dash_item, parent,false);
        final ViewHolder vHolder = new ViewHolder(v);
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(v.getContext(),ScannerActivity.class);
                intent.putExtra("SiteName",listItems.get(vHolder.getAdapterPosition()).getSiteName());
                v.getContext().startActivity(intent);
            }
        });


        return vHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        adminDashGridItem listItem = listItems.get(position);
        holder.siteNameTV.setText(listItem.getSiteName());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }






    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView siteNameTV;
        public CardView cardView;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            siteNameTV = (TextView)itemView.findViewById(R.id.siteTV);
            cardView = (CardView)itemView.findViewById(R.id.siteCV);
        }


    }


}
