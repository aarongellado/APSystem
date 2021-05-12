package com.example.apsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adminAttendanceAdapter extends RecyclerView.Adapter<adminAttendanceAdapter.ViewHolder>{
    public List<adminAttendanceItem> listItems;
    private Context context;

    public adminAttendanceAdapter(List<adminAttendanceItem> listItems, Context context){
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public adminAttendanceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_attendance_item, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        adminAttendanceItem listItem = listItems.get(position);
        holder.employeeID.setText(listItem.getEmployee_id());
        holder.date.setText(listItem.getDate());
        holder.timeIn.setText(listItem.getTime_in());
        holder.timeOut.setText(listItem.getTime_out());
        holder.siteName.setText(listItem.getSiteName());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView employeeID;
        public TextView date;
        public TextView timeIn;
        public TextView timeOut;
        public TextView siteName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            employeeID = (TextView) itemView.findViewById(R.id.empIDAttTV);
            date = (TextView) itemView.findViewById(R.id.dateAttTV);
            timeIn = (TextView) itemView.findViewById(R.id.timeInAttTV);
            timeOut = (TextView) itemView.findViewById(R.id.timeOutAttTV);
            siteName = (TextView) itemView.findViewById(R.id.siteNAttTV);


        }
    }
}
