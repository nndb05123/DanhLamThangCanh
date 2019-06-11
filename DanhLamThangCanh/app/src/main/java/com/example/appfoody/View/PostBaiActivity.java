package com.example.appfoody.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.Places;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfoody.Control.PostBaiController;
import com.example.appfoody.R;

import java.util.Set;

public class PostBaiActivity extends AppCompatActivity {
    static int PLACE_PICKER_REQUEST=101;
    static int PERMISSION_REQUEST_CODE;
    Button btnDangBai, btnChonAnh, btnLayToaDo;
    EditText txtTieuDe, txtNoiDung,txtDiaChi;
    TextView txtKinhDo, txtViDo;
    Location location;
    Context mContext;
    Activity mActivity;
    PostBaiController postBaiController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bai);
        postBaiController=new PostBaiController(this,this);

        // Initialize Places.
        Places.initialize(this, "AIzaSyA_yXGk2lddVkWbHCXtMyqv3VGjoqAkT2Y");

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);


        mActivity=this;
        mContext=this;
        AnhXa();
        SetEvents();

    }

    private void AnhXa() {
        txtTieuDe=findViewById(R.id.txtTieuDeBaiDang);
        txtNoiDung=findViewById(R.id.txtNoiDungPostBai);
        txtDiaChi= findViewById(R.id.txtDiaChiNewPost);

        btnChonAnh = findViewById(R.id.btnChoosePic);
        btnDangBai = findViewById(R.id.btnDangBai);
        btnLayToaDo = findViewById(R.id.btnGetToaDo);

        txtKinhDo= findViewById(R.id.txtKinhDo);
        txtViDo= findViewById(R.id.txtViDo);

    }

    private void SetEvents() {
        btnLayToaDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(mActivity), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
         });
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBaiController.SelectPicture();
            }
        });
        btnDangBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDung= txtNoiDung.getText().toString();
                String tieuDe=txtTieuDe.getText().toString();
                String diaChi= txtDiaChi.getText().toString();
                Double kinhDo=10.870153;
                Double viDo=106.802966;

                postBaiController.NewPost(noiDung,tieuDe,diaChi,kinhDo,viDo);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Toast.makeText(PostBaiActivity.this, "Permission denied to get your Location", Toast.LENGTH_SHORT).show();
            }
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("appcheck","activity return "+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PostBaiController.SELECT_PICTIRE_CALLBACK_CODE)
        {
            postBaiController.HandleCallBackActivitySelectPicture(requestCode,resultCode,data);
        }
        if(requestCode==PLACE_PICKER_REQUEST)
        {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
