package com.example.apsystem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class empLeaveAdapter extends RecyclerView.Adapter<empLeaveAdapter.ViewHolder> {

    private  List<empLeaveListItem> listItems;
    public String leaveStatusText;
    private Context context;

    public empLeaveAdapter(List<empLeaveListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        empLeaveListItem listItem = listItems.get(position);
        switch (listItem.getEmpLeaveStatus()){
            case "0":
                leaveStatusText = "Pending";
                break;
            case "1":
                leaveStatusText = "Approved";
                break;
            case "2":
                leaveStatusText = "Denied";
                break;

        }
        holder.leaveTypeTV.setText(listItem.getEmpLeaveType());
        holder.leaveStatusTV.setText(leaveStatusText);
        holder.leaveStartTV.setText(listItem.getEmpLeaveStart());
        holder.leaveEndTV.setText(listItem.getEmpLeaveEnd());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveTypeTV, leaveStatusTV, leaveStartTV, leaveEndTV;

        public ViewHolder(View itemView) {
            super(itemView);
            leaveTypeTV = (TextView)itemView.findViewById(R.id.leaveTypeTV);
            leaveStatusTV = (TextView)itemView.findViewById(R.id.leaveStatusTV);
            leaveStartTV = (TextView) itemView.findViewById(R.id.leaveStartTV);
            leaveEndTV = (TextView) itemView.findViewById(R.id.leaveEndTV);

        }
    }
}
