package com.example.appfoody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.appfoody.Adapters.AdapterRecyclerHinhAnh;
import com.example.appfoody.Adapters.ViewPageImageAdapter;
import com.example.appfoody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FullScreenListImageView extends AppCompatActivity {


    ViewPager viewPager;
    ArrayList<String> imageList= new ArrayList<>();
    ArrayList<Bitmap> bitmapList=new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_full_screen_list_image_view);




        imageList=getIntent().getStringArrayListExtra("imageList");
        viewPager= findViewById(R.id.viewPager);


        for (String imageChild:imageList) {

            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhdanhlam").child(imageChild);
            long ONE_MEGABYTE = 5 * 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size()==imageList.size())
                    {
                        ViewPageImageAdapter viewPageImageAdapter= new ViewPageImageAdapter(getApplicationContext(),bitmapList);
                        viewPager.setAdapter(viewPageImageAdapter);
                    }

                }
            });
        }














    }


}
