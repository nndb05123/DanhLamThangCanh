package com.example.appfoody.Control;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.appfoody.Model.DanhLamThangCanhModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class PostBaiController {
    public static int SELECT_PICTIRE_CALLBACK_CODE=1000;

    List<Uri> listImage;
    List<String> imagesEncodedList;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference nootRoot=firebaseDatabase.getReference();

    FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
    StorageReference storageReference=  firebaseStorage.getReference();

    Context mContext;
    Activity mActivity;
    FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();

    public PostBaiController(Context mContext, Activity mActivity) {
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void NewPost(String _noiDung, String _tieuDe,String _diaChi, Double _kinhDO, Double _viDo)
    {
        if(listImage==null||listImage.size()==0)
        {
            Toast.makeText(mContext,"Bạn phải chọn ít nhất 1 ảnh!",Toast.LENGTH_LONG);

        }
        else
        {
            final DanhLamThangCanhModel danhLamThangCanhModel= new DanhLamThangCanhModel(_tieuDe,"",_diaChi,_noiDung,_kinhDO,_viDo);
            final DatabaseReference newPost= nootRoot.child("danhlamthangcanhs").push();
            String ma=newPost.getKey();
            danhLamThangCanhModel.setMadanhlam(ma);
            final DatabaseReference newPostImage= nootRoot.child("hinhanhdanhlams").child(ma);
            final List<String> listNameFile= new ArrayList<>();
            for(int i=0;i<listImage.size();i++)
            {
                final String nameFile=ma+"_"+i+".jpg";
                StorageReference pictureStorage= storageReference.child("hinhdanhlam/"+nameFile);
                pictureStorage.putFile(listImage.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        listNameFile.add(nameFile);
                        if(listNameFile.size()==listImage.size());
                        {
                            newPost.setValue(danhLamThangCanhModel);
                            newPostImage.setValue(listNameFile);
                            Toast.makeText(mContext,"Đăng bài thành công!",Toast.LENGTH_LONG).show();

                        }

                    }
                });
            }


        }
    }
    public void SelectPicture()
    {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivity.startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTIRE_CALLBACK_CODE);
    }
    public void HandleCallBackActivitySelectPicture(int requestCode, int resultCode,Intent data)
    {
        if(requestCode==SELECT_PICTIRE_CALLBACK_CODE&&resultCode==Activity.RESULT_OK&&null!=data)
        {
            Log.d("pictureSelect",data.toString());
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            imagesEncodedList = new ArrayList<String>();

            if(data.getData()!=null)
            {
                Uri mImageUri=data.getData();

                Cursor cursor = mContext.getContentResolver().query(mImageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imageEncoded  = cursor.getString(columnIndex);
                imagesEncodedList.add(imageEncoded);
                cursor.close();
            }
            else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    listImage = new ArrayList<Uri>();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        listImage.add(uri);
                        // Get the cursor
                        Cursor cursor = mContext.getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imageEncoded  = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();

                    }
                    Log.v("LOG_TAG", "Selected Images" + listImage.size());
                }
            }
        }
    }
}
