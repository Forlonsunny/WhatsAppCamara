package com.theoaktroop.whatsappcamara.ImageTable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.theoaktroop.whatsappcamara.R;

import java.io.ByteArrayInputStream;
import java.util.List;



/**
 * Created by Suuny on 10/12/2015.
 */
public class ImageListAdapter extends BaseAdapter{

    public static final String TAG="ImageListAdapter";

   private List<ImageModule> mItems;
    private LayoutInflater mInflater;

    public ImageListAdapter(Context context, List<ImageModule> mItems) {
        this.mItems = mItems;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public ImageModule getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getImageId() : position;
    }
    public List<ImageModule> getItems()
    {
        return mItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_aswer_images_helper, parent, false);
            holder = new ViewHolder();
            holder.txtImageTitle = (TextView) v.findViewById(R.id.txt_question_title);
            holder.mImageView=(ImageView)v.findViewById(R.id.image_view);

            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }
        ImageModule currentItem=getItem(position);
        if(currentItem!=null)
        {
            holder.txtImageTitle.setText((int) currentItem.getImageId());
            if(currentItem.getImages()!=null)
            {
                byte[] outImages=currentItem.getImages();
                ByteArrayInputStream imagesStream= new ByteArrayInputStream(outImages);
                Bitmap theImages= BitmapFactory.decodeStream(imagesStream);
                holder.mImageView.setImageBitmap(theImages);
            }
        }
        return v;
    }
    class ViewHolder {
        TextView txtImageTitle;
        ImageView mImageView;

    }
}
