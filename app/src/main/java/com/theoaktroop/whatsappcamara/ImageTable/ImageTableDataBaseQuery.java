package com.theoaktroop.whatsappcamara.ImageTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.theoaktroop.whatsappcamara.DataBaseHalper.DbHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




/**
 * Created by Suuny on 10/14/2015.
 */
public class ImageTableDataBaseQuery {

    private DbHelper mImageDbHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;
    private String[] allColumns = {
            DbHelper.COLUMN_IMAGE_ID,
            DbHelper.COLUMN_ANSWER_FORM_ID,
            DbHelper.COLUMN_IMAGE,


    };

    public ImageTableDataBaseQuery(Context mContext) {
        this.mContext = mContext;
        this.mImageDbHelper = new DbHelper(mContext);

        try {
            open();
            System.out.println("DataBase open" +"ImageTableDataBaseQuery");
        } catch (SQLException e) {
            // Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void open() throws SQLException {
        mSqLiteDatabase = mImageDbHelper.getWritableDatabase();
    }

    public void close() {
        mImageDbHelper.close();

    }

    public ImageModule createNewImage(long answerId, byte[] images)
    {
        ContentValues values=new ContentValues();
        values.put(DbHelper.COLUMN_ANSWER_FORM_ID,answerId);
        values.put(DbHelper.COLUMN_IMAGE, images);


        

        long insertId=mSqLiteDatabase.insert(DbHelper.TABLE_NAME_IMAGE, null, values);
        System.out.println("insert id=" + insertId);
        Cursor cursor=mSqLiteDatabase.query(DbHelper.TABLE_NAME_IMAGE, allColumns, DbHelper.COLUMN_IMAGE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        ImageModule newImageModule=cursorToImageModule(cursor);
        cursor.close();
        System.out.println("Form ImageTable image added");

        return  newImageModule;



    }

    public List<ImageModule>getAllImageSavedQuestion() {
        List<ImageModule>listImageModules=new ArrayList<ImageModule>();
        Cursor cursor=mSqLiteDatabase.query(DbHelper.TABLE_NAME_IMAGE, allColumns, null, null, null, null, null);
        if (cursor!=null)
        {
            cursor.moveToFirst();
            while ((!cursor.isAfterLast())){
                ImageModule mImageModule=cursorToImageModule(cursor);
                listImageModules.add(mImageModule);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listImageModules;
    }

   public ImageModule getAllImageSavedAnswerByAnswerFromId(long id){

    Cursor cursor=mSqLiteDatabase.query(DbHelper.TABLE_NAME_IMAGE,allColumns,
            DbHelper.COLUMN_ANSWER_FORM_ID + "=? ",new String[]{String.valueOf(id)},null,null,null);
    if (cursor!=null) {
        cursor.moveToFirst();
    }
            ImageModule mImageModule=cursorToImageModule(cursor);




    return mImageModule;
   }

    protected ImageModule cursorToImageModule(Cursor cursor) {
        ImageModule mImageModule=new ImageModule();
        mImageModule.setImageId(cursor.getLong(0));
        mImageModule.setAnswerId(cursor.getInt(1));
        mImageModule.setImages(cursor.getBlob(2));



        return mImageModule;
    }
    public void ImageDeleteById(long ePID) throws SQLException {
        open();
        mSqLiteDatabase.delete(DbHelper.TABLE_NAME_IMAGE,
                DbHelper.COLUMN_ANSWER_FORM_ID + " = " + ePID, null);
        close();
    }
    public void ImageDeleteByImageTitle(String imageTitle) throws SQLException {
        open();
        mSqLiteDatabase.delete(DbHelper.TABLE_NAME_IMAGE,
                DbHelper.COLUMN_IMAGE_TITLE + " = " + imageTitle, null);
        close();
    }
    public void deleteAllTable()
    {


       // mSqLiteDatabase.delete(DbHelper.TABLE_NAME_QUESTIONNAIRE,null,null);
        //mSqLiteDatabase.delete(DbHelper.TABLE_NAME_Image,null,null);
        mSqLiteDatabase.delete(DbHelper.TABLE_NAME_IMAGE,null,null);


    }

    public void upDateImage(long insertId,long answerId, byte[] images)
    {
        ContentValues values=new ContentValues();
        values.put(DbHelper.COLUMN_ANSWER_FORM_ID,answerId);
        values.put(DbHelper.COLUMN_IMAGE,images);

        mSqLiteDatabase.update(DbHelper.TABLE_NAME_IMAGE, values, DbHelper.COLUMN_IMAGE_ID + " = " + insertId, null);
        close();
    }

}
