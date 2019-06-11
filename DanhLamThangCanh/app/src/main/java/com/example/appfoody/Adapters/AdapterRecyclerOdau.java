package com.example.appfoody.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.example.appfoody.R;
import com.example.appfoody.View.ChiTietDanhLamActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;
;

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {

    List<DanhLamThangCanhModel> danhLamThangCanhModelList;
    int resource;
    Context context;

    public AdapterRecyclerOdau(Context context, List<DanhLamThangCanhModel> danhLamThangCanhModelList, int resource) {
        this.danhLamThangCanhModelList = danhLamThangCanhModelList;
        this.resource = resource;
        this.context=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinhAnhDanhLam;
        TextView txtTenDanhLamThangCanh,txtDiaChiDanhLam;
        LinearLayout containerBinhLuan;
        CardView cardViewTrangChu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTenDanhLamThangCanh = itemView.findViewById(R.id.txtTenDanhLamThangCanh);
            imgHinhAnhDanhLam = itemView.findViewById(R.id.imgHinhAnhDanhLam);
            txtDiaChiDanhLam=itemView.findViewById(R.id.txtDiaChiDanhLam);
            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            cardViewTrangChu=itemView.findViewById(R.id.cardViewTrangChu);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerOdau.ViewHolder viewHolder, int i) {
        final DanhLamThangCanhModel danhLamThangCanhModel = danhLamThangCanhModelList.get(i);
        viewHolder.txtTenDanhLamThangCanh.setText(danhLamThangCanhModel.getTendanhlam());
        if (danhLamThangCanhModel.getHinhanhdanhlam().size() > 0) {
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(danhLamThangCanhModel.getHinhanhdanhlam().get(0));
            long ONE_MEGABYTE = 5 * 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    viewHolder.imgHinhAnhDanhLam.setImageBitmap(bitmap);
                }
            });
        }
        viewHolder.cardViewTrangChu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietDanhLam=new Intent(context, ChiTietDanhLamActivity.class);
                iChiTietDanhLam.putExtra("danhlamthangcanh",danhLamThangCanhModel);
                context.startActivity(iChiTietDanhLam);
            }
        });
    }


    @Override
    public int getItemCount() {
        return danhLamThangCanhModelList.size();
    }

}
