package com.example.apsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class empProfFragment extends Fragment {
    private TextView FNameTV, LNameTV, GenderTV, BDateTV, AddTV, CInfoTV;
    private ImageView userQR;
    public empProfFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emp_prof, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FNameTV = (TextView) getView().findViewById(R.id.PFNamTxtV);
        LNameTV = (TextView) getView().findViewById(R.id.PLNameTxtV);
        GenderTV = (TextView) getView().findViewById(R.id.PGendTxtV);
        BDateTV = (TextView) getView().findViewById(R.id.PBDateTxtV);
        AddTV = (TextView) getView().findViewById(R.id.PAddTxtV);
        userQR = (ImageView) getView().findViewById(R.id.QRIdIV);
        CInfoTV = (TextView) getView().findViewById(R.id.CInfoTV);

        FNameTV.setText(SharedPrefManager.getInstance(getActivity()).getKeyEidFname());
        LNameTV .setText(SharedPrefManager.getInstance(getActivity()).getKeyEidLname());
        GenderTV.setText(SharedPrefManager.getInstance(getActivity()).getKeyEidGender());
        BDateTV.setText(SharedPrefManager.getInstance(getActivity()).getKeyEidBdate());
        AddTV.setText(SharedPrefManager.getInstance(getActivity()).getKeyEidAddress());
        CInfoTV.setText(SharedPrefManager.getInstance(getActivity()).getKeyEidCinfo());

        QRgen();



    }

    private void QRgen() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(SharedPrefManager.getInstance(getActivity()).getKeyEid(),
                    BarcodeFormat.QR_CODE, 500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            userQR.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }
}
