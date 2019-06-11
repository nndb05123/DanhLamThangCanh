package com.example.appfoody.Model;

import android.support.annotation.NonNull;

import com.example.appfoody.Control.Interfaces.BinhLuanInterface;
import com.example.appfoody.Control.Interfaces.DangBinhLuanInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BinhLuanModel {

    String idDanhlam;
    DatabaseReference nootRoot;

    public BinhLuanModel(String id)
    {
        this.idDanhlam=id;
        nootRoot = FirebaseDatabase.getInstance().getReference();
    }



    public  void ThemBinhLuan(String maDanhLam,BinhLuanModel binhLuanModel)
    {
        DatabaseReference nodeBinhLuan= FirebaseDatabase.getInstance().getReference().child("binhluandanhlams");
        String maBinhLuan= nodeBinhLuan.child(maDanhLam).push().getKey();
        nodeBinhLuan.child(maDanhLam).child(maBinhLuan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    public static void dangBinhLuan(final BinhLuanCon binhLuanCon, String idDanhlam, final DangBinhLuanInterface dangBinhLuanInterface)
    {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("binhluandanhlams").child(idDanhlam).push();

        ref.setValue(binhLuanCon);

        final DatabaseReference DLTCref= FirebaseDatabase.getInstance().getReference().child("danhlamthangcanhs").child(idDanhlam);
        DLTCref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DanhLamThangCanhModel danhLamThangCanhModel= dataSnapshot.getValue(DanhLamThangCanhModel.class);
                Double soLuongCmt=danhLamThangCanhModel.getCountCmt();
                Double avgMark=danhLamThangCanhModel.getAvgMark();
                avgMark=(avgMark*soLuongCmt+ binhLuanCon.getMark())/(soLuongCmt+1);
                soLuongCmt+=1;
                DLTCref.child("avgMark").setValue(avgMark);
                DLTCref.child("countCmt").setValue(soLuongCmt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(ref.getKey()==null)
        {
            dangBinhLuanInterface.CallBackDangBinhLuan(0);

        }
        else
        {
            dangBinhLuanInterface.CallBackDangBinhLuan(1);
        }
    }
    public void getDanhSachBinhLuan(final BinhLuanInterface binhLuanInterface)
    {
        DatabaseReference nodeBinhLuan= FirebaseDatabase.getInstance().getReference().child("binhluandanhlams").child(idDanhlam);
        nodeBinhLuan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();
                for (DataSnapshot child :children) {
                    BinhLuanCon binhLuanCon= child.getValue(BinhLuanCon.class);
                    binhLuanInterface.getDanhSachBinhLuan(binhLuanCon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
