package com.example.apsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref";
    private static final String KEY_EID = "employee_id";
    private static final String KEY_EID_FNAME = "firstname";
    private static final String KEY_EID_LNAME = "lastname";
    private static final String KEY_EID_ADDRESS = "address";
    private static final String KEY_EID_BDATE = "birthdate";
    private static final String KEY_EID_CINFO = "contact_info";
    private static final String KEY_EID_GENDER = "gender";
    private static final String KEY_EID_POSID = "position_id";
    private static final String KEY_EID_SCHEDID = "schedule_id";
    private static final String KEY_EID_AUTH = "userAuth";
    private static final String KEY_EID_CREATEDON = "created_on";
    private static final String KEY_ESCAN_ID = "escanID";


    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(String employee_id, String firstname, String lastname, String address, String birthdate,
                             String contact_info, String gender,String position_id, String schedule_id, String userAuth,
                             String created_on){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EID,employee_id);
        editor.putString(KEY_EID_FNAME,firstname);
        editor.putString(KEY_EID_LNAME, lastname);
        editor.putString(KEY_EID_ADDRESS,address);
        editor.putString(KEY_EID_BDATE,birthdate);
        editor.putString(KEY_EID_CINFO,contact_info);
        editor.putString(KEY_EID_GENDER,gender);
        editor.putString(KEY_EID_POSID,position_id);
        editor.putString(KEY_EID_SCHEDID,schedule_id);
        editor.putString(KEY_EID_AUTH,userAuth);
        editor.putString(KEY_EID_CREATEDON,created_on);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_EID, null)!=null){
            return true;
        }
        return false;
    }

    public String getKeyEid(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID,null);
    }

    public String getKeyEidFname(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_FNAME,null);
    }

    public String getKeyEidLname(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_LNAME,null);
    }

    public String getKeyEidAddress(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_ADDRESS,null);
    }

    public String getKeyEidBdate(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_BDATE,null);
    }

    public String getKeyEidCinfo(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_CINFO,null);
    }

    public String getKeyEidGender(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_GENDER,null);
    }

    public String getKeyEidPosid(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_POSID,null);
    }

    public String getKeyEidSchedid(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_SCHEDID,null);
    }

    public String getKeyEidAuth(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_AUTH,null);
    }
    public String getKeyEidCreatedon(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EID_CREATEDON,null);

    }public String getKeyEscanId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ESCAN_ID,null);
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public boolean escanId(String eScanID){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ESCAN_ID,eScanID);
        editor.apply();
        return true;
    }


}
