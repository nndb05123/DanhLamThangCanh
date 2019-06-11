package com.example.appfoody.View;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;


import com.example.appfoody.Control.DanhLamController;
import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;

public class TrangChuActivity extends AppCompatActivity{
    public static int CALLBACK_LOGIN_CODE=99;
    RecyclerView recyclerViewdata;
    FloatingActionButton fab;
    SearchView searchView;
    DanhLamController odauControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        recyclerViewdata=findViewById(R.id.recylerOdau);
        searchView= findViewById(R.id.searchView);
        fab=findViewById(R.id.fab);



    }

    @Override
    protected void onStart() {
        super.onStart();
        odauControl = new DanhLamController(this, recyclerViewdata);
        odauControl.getDanhSachQuanAnController(this, recyclerViewdata, false,"");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!s.equals(""))
                {
                    Log.d("appcheck","query: "+s);
                    odauControl.getDanhSachQuanAnController(getApplicationContext(),recyclerViewdata,true, s);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DangNhapActivity.KIEMTRA_PROVIDER_DANGNHAP==0)
                {
                    Intent login= new Intent(getApplicationContext(),DangNhapActivity.class);
                    startActivityForResult(login,CALLBACK_LOGIN_CODE);

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CALLBACK_LOGIN_CODE)
        {
            if(resultCode==DangNhapActivity.RESULT_LOGIN_SUCCESS)
            {
                Intent intentNewPost= new Intent(this,PostBaiActivity.class);
                startActivity(intentNewPost);
            }
        }
    }
}
