package com.example.appfoody.View;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfoody.Control.BinhLuanController;
import com.example.appfoody.Control.Interfaces.DangBinhLuanInterface;
import com.example.appfoody.Model.BinhLuanCon;
import com.example.appfoody.Model.BinhLuanModel;
import com.example.appfoody.R;

import java.util.ArrayList;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<ImageButton> listStar = new ArrayList<>();
    ImageButton star1,star2,star3,star4,star5;
    TextView txtTenDL,txtDiaChiDanhLam,txtDangBinhLuan;
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    String maDanhLam;
    BinhLuanController binhLuanController;
    BinhLuanModel binhLuanModel;
    int mark=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        maDanhLam=getIntent().getStringExtra("maDanhLam");



        AddControl();
        txtDiaChiDanhLam=findViewById(R.id.txtDiaChiDanhLam);
        txtTenDL=findViewById(R.id.txtTenDL);



        txtDangBinhLuan=findViewById(R.id.txtDangBinhLuan);
        txtDangBinhLuan.setOnClickListener(this);
        star1.setOnClickListener(this);
        star2.setOnClickListener(this);
        star3.setOnClickListener(this);
        star4.setOnClickListener(this);
        star5.setOnClickListener(this);

    }
    public  void AddControl()
    {

        edNoiDungBinhLuan=findViewById(R.id.edNoiDungBinhLuan);
        edTieuDeBinhLuan=findViewById(R.id.edTieuDeBinhLuan);
        star1=findViewById(R.id.star1);
        star2=findViewById(R.id.star2);
        star3=findViewById(R.id.star3);
        star4=findViewById(R.id.star4);
        star5=findViewById(R.id.star5);

        listStar.add(star1);
        listStar.add(star2);
        listStar.add(star3);
        listStar.add(star4);
        listStar.add(star5);
    }
    public  void onClick(View v)
    {
        int id=v.getId();
        switch (id)
        {
            case R.id.txtDangBinhLuan:
                binhLuanModel= new BinhLuanModel(maDanhLam);
                String tieude=edTieuDeBinhLuan.getText().toString();
                String noidung=edNoiDungBinhLuan.getText().toString();
                BinhLuanCon binhLuanCon= new BinhLuanCon();
                binhLuanCon.setMark((double) mark);
                binhLuanCon.setNoiDung(tieude);
                binhLuanCon.setUserName(noidung);
                DangBinhLuanInterface dangBinhLuanInterface= new DangBinhLuanInterface() {
                    @Override
                    public void CallBackDangBinhLuan(int i) {
                        if(i==1)
                        {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","Đăng bình luận thành công!");
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                        if(i==0)
                        {
                            Toast.makeText(getApplicationContext(),"Đăng bình luận thất bại",Toast.LENGTH_LONG);
                        }
                    }
                };
                BinhLuanModel.dangBinhLuan(binhLuanCon,maDanhLam,dangBinhLuanInterface );
                break;
            case R.id.star1:
            {
                setStar(1);
                break;
            }
            case R.id.star2:
            {
                setStar(2);
                break;
            }
            case R.id.star3:
            {
                setStar(3);
                break;
            }
            case R.id.star4:
            {
                setStar(4);
                break;
            }
            case R.id.star5:
            {
                setStar(5);
                break;
            }
        }
    }
    public void setStar(int index)
    {
        mark=index;
        for(int i=0;i<index;i++)
        {
            listStar.get(i).setImageDrawable(getResources().getDrawable(R.drawable.star_max));

        }
        for(int i=index;i<5;i++)
        {
            listStar.get(i).setImageDrawable(getResources().getDrawable(R.drawable.star_min));
        }
    }
}
