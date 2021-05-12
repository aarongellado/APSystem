package com.example.apsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText UNLoginET, PWLoginET;
    private Button SignInLoginBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            if (SharedPrefManager.getInstance(this).getKeyEidAuth().equals("1")) {
                startActivity(new Intent(this, AdminProfActivity.class));
                return;
            } else {
                startActivity(new Intent(this, EmployeeProfActivity.class));
                return;
            }
        }

        UNLoginET = (EditText) findViewById(R.id.UNLoginET);
        PWLoginET = (EditText) findViewById(R.id.PWLoginET);
        SignInLoginBtn = (Button) findViewById(R.id.SignInLoginBtn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in");
        SignInLoginBtn.setOnClickListener(this);

    }
    private void userLogin() {
        final String username = UNLoginET.getText().toString().trim();
        final String password = PWLoginET.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("employee_id"),
                                                obj.getString("firstname"),
                                                obj.getString("lastname"),
                                                obj.getString("address"),
                                                obj.getString("birthdate"),
                                                obj.getString("contact_info"),
                                                obj.getString("gender"),
                                                obj.getString("position_id"),
                                                obj.getString("schedule_id"),
                                                obj.getString("userAuth"),
                                                obj.getString("created_on")
                                        );
                                if (SharedPrefManager.getInstance(getApplicationContext()).getKeyEidAuth().equals("1")) {
                                    startActivity(new Intent(getApplicationContext(), AdminProfActivity.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), EmployeeProfActivity.class));
                                    finish();
                                }


                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
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
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("employee_id", username);
                params.put("employee_pass", password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view) {
        if (view == SignInLoginBtn) {
            userLogin();
        }
    }
}
