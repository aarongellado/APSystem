package com.example.apsystem;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class adminAttendanceFragment extends Fragment {
    private TextView EIDTV, DateTV, TimeInTV, TimeOutTV, SiteNameTV;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<adminAttendanceItem> listItems;
    private ProgressDialog progressDialog;


    public adminAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_attendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getView().findViewById(R.id.AttListRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());

        loadAttendance();
    }

    private void loadAttendance() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Attendance");
        progressDialog.show();
        String URLHolder;
        if(SharedPrefManager.getInstance(getActivity()).getKeyEidAuth().equals("1")){
            URLHolder = Constants.URL_ADMIN_ATTENDANCE_LIST;
        }
        else{URLHolder = Constants.URL_EMPLOYEE_ATTENDANCE_LIST;};
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLHolder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject listObj = obj.getJSONObject(i);
                                String eidFetched = listObj.getString("employee_id");
                                String dateFetched = listObj.getString("date");
                                String timeInFetched = listObj.getString("time_in");
                                String timeOutFetched = listObj.getString("time_out");
                                String siteNameFetched = listObj.getString("siteName");
                                adminAttendanceItem item = new adminAttendanceItem(eidFetched, dateFetched, timeInFetched, timeOutFetched, siteNameFetched);
                                listItems.add(item);

                            }
                            adapter = new adminAttendanceAdapter(listItems, getContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", SharedPrefManager.getInstance(getActivity()).getKeyEid());
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}
