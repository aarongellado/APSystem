package com.example.apsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class empLeaveFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private Spinner leaveTypeSpinner;
    private EditText leaveStartET;
    private EditText leaveEndET;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<empLeaveListItem> listItems;
    private Integer DateSwitch;
    private ProgressDialog progressDialog;
    String leaveTypeSelect;
    String leaveSDateChecker, leaveEDateChecker;


    public empLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        leaveStartET = (EditText) getView().findViewById(R.id.leaveStartET);
        leaveEndET = (EditText) getView().findViewById(R.id.leaveEndET);
        leaveStartET.setFocusable(false);
        leaveStartET.setClickable(false);
        leaveEndET.setFocusable(false);
        leaveEndET.setClickable(false);
        ImageView leaveEnd = (ImageView) getView().findViewById(R.id.leaveEndIV);
        ImageView leaveStart = (ImageView) getView().findViewById(R.id.leaveStartIV);
        Button submitLeaveBtn = (Button) getView().findViewById(R.id.submitLeaveBtn);
        recyclerView = (RecyclerView) getView().findViewById(R.id.leaveListRV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        progressDialog = new ProgressDialog(getContext());

        leaveTypeSpinner = getView().findViewById(R.id.leaveTypeSpinner);
        List<String> leaveType = new ArrayList<>();
        leaveType.add(0, "Type of Leave");
        leaveType.add("Sick Leave");
        leaveType.add("Vacation Leave");
        leaveType.add("Maternity Leave");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, leaveType);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveTypeSpinner.setAdapter(dataAdapter);
        leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                leaveTypeSelect = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        loadLeaveList();
        leaveStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSwitch=1;
                showDatePicker();
            }
        });
        leaveEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateSwitch=2;
                showDatePicker();
            }
        });
        submitLeaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(leaveTypeSelect != "Type of Leave"){
                    if(leaveSDateChecker!=null && leaveEDateChecker!=null){
                        submitLeave();
                        listItems.clear();
                        loadLeaveList();
                    }
                    else {
                        Toast.makeText(getContext(), "Select Date", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "Select Type of Leave", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void submitLeave() {
        final String empID = SharedPrefManager.getInstance(getActivity()).getKeyEid();
        final String empName = SharedPrefManager.getInstance(getActivity()).getKeyEidFname()
                + " " + SharedPrefManager.getInstance(getActivity()).getKeyEidLname();
        final String empLeaveStartDate = leaveStartET.getText().toString().trim();
        final String empLeaveEndDate = leaveEndET.getText().toString().trim();
        final String sleaveType = leaveTypeSelect;

        progressDialog.setMessage("Submitting Leave Request...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_EMPLOYEE_LEAVE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    leaveTypeSpinner.setSelection(0);
                    leaveStartET.getText().clear();
                    leaveEndET.getText().clear();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", empID);
                params.put("employee_name", empName);
                params.put("leave_type", sleaveType);
                params.put("leaveDateStart", empLeaveStartDate);
                params.put("leaveDateEnd", empLeaveEndDate);
                params.put("leaveRequestStatus", "0");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void loadLeaveList() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Appointments");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_EMPLOYEE_LEAVE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray obj = new JSONArray(response);
                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject listObj = obj.getJSONObject(i);
                                String leaveTypeFetched = listObj.getString("leave_type");
                                String leaveStatusFetched = listObj.getString("leaveRequestStatus");
                                String leaveStartFetched = listObj.getString("leaveDateStart");
                                String leaveEndFetched = listObj.getString("leaveDateEnd");
                                empLeaveListItem item = new empLeaveListItem(leaveStartFetched, leaveEndFetched,leaveStatusFetched,leaveTypeFetched );
                                listItems.add(item);

                            }
                            adapter = new empLeaveAdapter(listItems, getContext());
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_emp_leave, container, false);
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int yyyy, int mm, int dd) {
        String date, ddMod, mmMod;
        date = "";
        mm = mm + 1;
        mmMod = Integer.toString(mm);
        ddMod = Integer.toString(dd);

        if (mm < 10) {
            mmMod = "0" + mm;
        }
        if(dd<10){
            ddMod = "0"+dd;
        }
        date = +yyyy + "/" + mmMod + "/" + ddMod;

        switch (DateSwitch){
            case 1:
                leaveStartET.setText(date);
                leaveSDateChecker = leaveStartET.getText().toString().trim();
                break;
            case 2:
                leaveEndET.setText(date);
                leaveEDateChecker = leaveEndET.getText().toString().trim();
                break;
        }


    }

}
