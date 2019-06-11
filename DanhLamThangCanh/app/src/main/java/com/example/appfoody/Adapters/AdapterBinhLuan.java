package com.example.appfoody.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appfoody.Model.BinhLuanCon;
import com.example.appfoody.R;

import java.util.ArrayList;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewBinhLuan> {
    Context context;
    int resource;
    ArrayList<BinhLuanCon> listBinhLuan;
    public  AdapterBinhLuan(  Context context,int resource, ArrayList<BinhLuanCon> listBinhLuan)
    {
        this.context=context;
        this.resource=resource;
        this.listBinhLuan=listBinhLuan;

    }
    public class ViewBinhLuan extends RecyclerView.ViewHolder {
        TextView txtNoiDungBinhLuan,txtUserNamBinhLuan,txtDiemDanhGiaBinhLuan;

        public ViewBinhLuan(@NonNull View itemView) {
            super(itemView);
            txtNoiDungBinhLuan=itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtUserNamBinhLuan=itemView.findViewById(R.id.txtUserNamBinhLuan);
            txtDiemDanhGiaBinhLuan=itemView.findViewById(R.id.txtChamDiemBinhLuan);
        }
    }
    @NonNull
    @Override
    public AdapterBinhLuan.ViewBinhLuan onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        ViewBinhLuan viewHolder = new ViewBinhLuan(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBinhLuan viewBinhLuan, int i) {
        BinhLuanCon binhLuanCon=listBinhLuan.get(i);
        viewBinhLuan.txtDiemDanhGiaBinhLuan.setText(String.valueOf( binhLuanCon.getMark()));
        viewBinhLuan.txtUserNamBinhLuan.setText(binhLuanCon.getNoiDung());
        viewBinhLuan.txtUserNamBinhLuan.setText(binhLuanCon.getUserName());

    }


    @Override
    public int getItemCount() {
        return listBinhLuan.size();
    }
}
