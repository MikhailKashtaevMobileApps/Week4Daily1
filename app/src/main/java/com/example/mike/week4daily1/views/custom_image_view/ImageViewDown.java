package com.example.mike.week4daily1.views.custom_image_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.mike.week4daily1.views.MainActivity;

import java.io.InputStream;
import java.util.HashMap;

public class ImageViewDown extends android.support.v7.widget.AppCompatImageView {

    public ImageViewDown(Context context) {
        super(context);
    }

    public ImageViewDown(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewDown(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("StaticFieldLeak")
    public void setImageURL(final String URL){

        if( ((MainActivity)this.getContext()).imageCache.containsKey(URL) ){
            ImageViewDown.this.setImageBitmap( ((MainActivity)this.getContext()).imageCache.get(URL) );
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(URL).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);

                    Handler myHandler = new Handler(ImageViewDown.this.getContext().getMainLooper());
                    final Bitmap finalMIcon1 = mIcon11;
                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)ImageViewDown.this.getContext()).imageCache.put(URL, finalMIcon1);
                            ImageViewDown.this.setImageBitmap(finalMIcon1);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
