package com.quickr.cars.Helper;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class RemoteImage extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private LruCache<String, Bitmap> _memoryCache;

    public RemoteImage(ImageView view) {
        imageView = view;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 6;

        _memoryCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

    }

    @Override
    protected Bitmap doInBackground(String[] url) {
        Bitmap imageBitmap = getBitmapFromMemCache(url[0]);

        try {
            if (imageBitmap != null) {
                return imageBitmap;
            } else {
                URL imageURL = new URL(url[0]);
                imageBitmap = BitmapFactory.decodeStream(imageURL.openStream());
                return imageBitmap;
            }

        } catch (IOException e) {
            // Todo : Handle exception
        }

        return imageBitmap;

    }


    protected void storeInCache(String key, Bitmap bitmap) {

        if (getBitmapFromMemCache(key) == null) {
            _memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return _memoryCache.get(key);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        if (result != null)
            imageView.setImageBitmap(result);

    }
}
