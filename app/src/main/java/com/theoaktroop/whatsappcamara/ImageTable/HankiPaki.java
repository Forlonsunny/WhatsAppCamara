package com.theoaktroop.whatsappcamara.ImageTable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.theoaktroop.whatsappcamara.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

/**
 * Created by Suuny on 10/25/2015.
 */
public class HankiPaki {
//    private LinearLayout mLinearLayout;
//
//    ImageModule imageModule;
//    ImageTableDataBaseQuery imageTableDataBaseQuery;
//    ImageView imgUser ;
//    byte[] byteArray;
//
//    private String selectedImagePath = "";
//    final private int PICK_IMAGE = 1;
//    final private int CAPTURE_IMAGE = 2;
//    private String imgPath;
//    private int  index=1;
//
//    public Uri setImageUri() {
//        // Store image in dcim
//        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
//        Uri imgUri = Uri.fromFile(file);
//        this.imgPath = file.getAbsolutePath();
//        return imgUri;
//    }
//
//
//    public String getImagePath() {
//        return imgPath;
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mLinearLayout = new LinearLayout(this);
//        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
//        LinearLayout mLinearLayoutHorizontal=new LinearLayout(this);
//        mLinearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);
//
//        ImageButton caButton = new ImageButton(this);
//        caButton.setImageResource(R.drawable.camara_icon);
//        mLinearLayoutHorizontal.addView(caButton);
//        ImageButton btnGallery = new ImageButton(this);
//        btnGallery.setImageResource(R.drawable.gallery);
//
//        mLinearLayoutHorizontal.addView(btnGallery);
//        mLinearLayoutHorizontal.setGravity(Gravity.CENTER);
//        mLinearLayout.addView(mLinearLayoutHorizontal);
//        imgUser = new ImageView(this);
//        mLinearLayout.addView(imgUser);
//        imageTableDataBaseQuery = new ImageTableDataBaseQuery(this);
//        setContentView(mLinearLayout);
//        caButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//
//                    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
//                    startActivityForResult(intent, CAPTURE_IMAGE);
//
//                } catch (Exception e) {
//
//                }
//
//
//            }
//        });
//        btnGallery.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                try {
//
//                    Intent intent = new Intent();
//                    intent.setType("image/*");
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
//
//                } catch (Exception e) {
//
//                }
//
//
//            }
//        });
//
//
//    }
//
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != Activity.RESULT_CANCELED) {
//
//            if (requestCode == PICK_IMAGE) {
//                selectedImagePath = getAbsolutePath(data.getData());
//                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
//                System.out.println("Pick Image"+selectedImagePath);
//
//                Bitmap  bitmap = decodeFile(selectedImagePath);
//                SaveToDb(bitmap,selectedImagePath);
//
//
//            }else if (requestCode == CAPTURE_IMAGE) {
//                selectedImagePath = getImagePath();
//                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
//                System.out.println("Capture Image"+selectedImagePath);
//                Bitmap  bitmap = decodeFile(selectedImagePath);
//                SaveToDb(bitmap,selectedImagePath);
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//
//    }
//    public Bitmap decodeFile(String path) {
//        try {
//            // Decode image size
//            BitmapFactory.Options o = new BitmapFactory.Options();
//            o.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(path, o);
//            // The new size we want to scale to
//            final int REQUIRED_SIZE = 70;
//
//            // Find the correct scale value. It should be the power of 2.
//            int scale = 1;
//            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
//                scale *= 2;
//
//            // Decode with inSampleSize
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inSampleSize = scale;
//            return BitmapFactory.decodeFile(path, o2);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
//
//    public String getAbsolutePath(Uri uri) {
//        String[] projection = { MediaStore.MediaColumns.DATA };
//        @SuppressWarnings("deprecation")
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        if (cursor != null) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        } else
//            return null;
//    }
//    private void SaveToDb(final Bitmap bitmap2, final String selectedImageTitle)
//    {
//        try {
//
//
//            final CheckBox mCheckBox = new CheckBox(this);
//            final String ImgTitle="Save Picture";
//            mCheckBox.setText(ImgTitle);
//            //mCheckBox.setTextColor();
//            mLinearLayout.addView(mCheckBox);
//            mCheckBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mCheckBox.isChecked()) {
//                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                        byteArray = stream.toByteArray();
//
//                        imageModule = imageTableDataBaseQuery.createNewImage(3, byteArray);
//                        Toast.makeText(getApplicationContext(), "picture Saved in Database", Toast.LENGTH_LONG).show();
//
//                    }
//                    if (!mCheckBox.isChecked())
//                    {  mCheckBox.setChecked(true);
////                        CheckBox mchText=(CheckBox)v;
////                        try {
////                            String title=mchText.getText().toString();
////                            imageTableDataBaseQuery.ImageDeleteById(3);
////                            Toast.makeText(getApplicationContext(), "picture Delete in Database", Toast.LENGTH_LONG).show();
////                        } catch (SQLException e) {
////                            e.printStackTrace();
////                        }
//                    }
//
//                }
//            });
//
//
//        }catch (Exception e) {
//            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
//        }
//    }


}
