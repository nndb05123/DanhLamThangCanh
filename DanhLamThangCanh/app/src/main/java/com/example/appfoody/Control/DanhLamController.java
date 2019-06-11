package com.example.appfoody.Control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appfoody.Adapters.AdapterRecyclerOdau;
import com.example.appfoody.Control.Interfaces.DanhLamInterface;
import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;
import com.example.appfoody.View.DangNhapActivity;

import java.util.ArrayList;
import java.util.List;

public class DanhLamController {
    public static int CALLBACK_LOGIN_CODE=99;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    DanhLamThangCanhModel danhLamThangCanhModel;
    AdapterRecyclerOdau adapterRecyclerOdau;
    List<DanhLamThangCanhModel> danhLamThangCanhModelList = new ArrayList<>();;
    public DanhLamController(Context context, RecyclerView recyclerView){
        this.context = context;
        danhLamThangCanhModel = new DanhLamThangCanhModel();
        layoutManager = new LinearLayoutManager(context);
        this.recyclerView=recyclerView;
        recyclerView.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context, danhLamThangCanhModelList, R.layout.custom_layout_recyclerview_danhlam);
        recyclerView.setAdapter(adapterRecyclerOdau);

    }

    public void getDanhSachQuanAnController(Context context,RecyclerView recyclerOdau, boolean isQuery, String query){





        DanhLamInterface danhLamInterface = new DanhLamInterface() {
            @Override
            public void getDanhSachDanhLamThangCanh(DanhLamThangCanhModel danhLamThangCanhModel) {
                danhLamThangCanhModelList.add(danhLamThangCanhModel);
                adapterRecyclerOdau.notifyDataSetChanged();
            }
        };
        if(isQuery)
        {
            danhLamThangCanhModelList.clear();
            adapterRecyclerOdau.notifyDataSetChanged();
            danhLamThangCanhModel.getDanhSachDanhLamThangCanhwithQuery(danhLamInterface,query);

        }
        else{
            danhLamThangCanhModel.getDanhSachDanhLamThangCanh(danhLamInterface);
        }

    }


}
