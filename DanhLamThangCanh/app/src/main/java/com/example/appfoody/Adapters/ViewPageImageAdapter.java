package com.example.appfoody.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ViewPageImageAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<Bitmap> bitmaps;


    public ViewPageImageAdapter(Context mContext, ArrayList<Bitmap> bitmaps)
    {
        this.mContext=mContext;
        this.bitmaps=bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView= new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmaps.get(position));
        container.addView(imageView,0);
        return imageView;
    }
}
