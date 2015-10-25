package com.theoaktroop.whatsappcamara;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.theoaktroop.whatsappcamara.DataBaseHalper.DbHelper;
import com.theoaktroop.whatsappcamara.ImageTable.ImageModule;
import com.theoaktroop.whatsappcamara.ImageTable.ImageTableDataBaseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.File;
import java.sql.SQLException;
import java.util.Date;


public class MainActivity extends ActionBarActivity{
   private LinearLayout mLinearLayout;
   private LinearLayout mLayoutButtonAndImage;

    ImageModule imageModule;
    ImageTableDataBaseQuery imageTableDataBaseQuery;
    ImageView imgUser ;
    byte[] byteArray;

    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    private Button mSubmitButton;
    private Button mCancelButton;
    private int  indexFlag=0;
    private ScrollView scrollView;

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
    }


    public String getImagePath() {
        return imgPath;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        mLinearLayout = new LinearLayout(this);

        mLinearLayout.setVerticalFadingEdgeEnabled(true);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout mLinearLayoutHorizontal=new LinearLayout(this);
        mLinearLayoutHorizontal.setOrientation(LinearLayout.HORIZONTAL);

        ImageButton caButton = new ImageButton(this);
        caButton.setImageResource(R.drawable.camara_icon);
       mLinearLayoutHorizontal.addView(caButton);

        mLinearLayoutHorizontal.setGravity(Gravity.CENTER);
        mLinearLayout.addView(mLinearLayoutHorizontal);



        imageTableDataBaseQuery = new ImageTableDataBaseQuery(this);
        scrollView.addView(mLinearLayout);
        setContentView(scrollView);
        caButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {    if(indexFlag==1) {
                    mLayoutButtonAndImage.setVisibility(View.GONE);
                    indexFlag=0;

                }

                    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                    startActivityForResult(intent, CAPTURE_IMAGE);

                } catch (Exception e) {

                }


            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {

            if (requestCode == CAPTURE_IMAGE) {
                selectedImagePath = getImagePath();
                //imgUser.setImageBitmap(decodeFile(selectedImagePath));
                System.out.println("Capture Image"+selectedImagePath);
                Bitmap  bitmap = decodeFile(selectedImagePath);
                SaveToDb(bitmap,selectedImagePath);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 160;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    private void SaveToDb(final Bitmap bitmap2, final String selectedImageTitle)
    {
        try {
            mLayoutButtonAndImage=new LinearLayout(this);
            mLayoutButtonAndImage.setOrientation(LinearLayout.VERTICAL);

            imgUser = new ImageView(this);
            mSubmitButton=new Button(this);
            mCancelButton=new Button(this);
            mSubmitButton.setText("Submit This Picture");
            mCancelButton.setText("Cancel This Picture");

            mLayoutButtonAndImage.addView(imgUser);
            LinearLayout mHorizontalButtonLayout=new LinearLayout(this);
            mHorizontalButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
            mHorizontalButtonLayout.addView(mSubmitButton);
            mHorizontalButtonLayout.addView(mCancelButton);
            mHorizontalButtonLayout.setGravity(Gravity.CENTER);
            mLayoutButtonAndImage.addView(mHorizontalButtonLayout);

            mLinearLayout.addView(mLayoutButtonAndImage);
            indexFlag=1;
            imgUser.setImageBitmap(bitmap2);

            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byteArray = stream.toByteArray();

                    imageModule = imageTableDataBaseQuery.createNewImage(3, byteArray);
                    Toast.makeText(getApplicationContext(), "picture Saved in Database", Toast.LENGTH_LONG).show();
                    mLayoutButtonAndImage.setVisibility(View.GONE);


                }
            });
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLayoutButtonAndImage.setVisibility(View.GONE);
                }
            });




        }catch (Exception e) {
            Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
        }
    }


}
