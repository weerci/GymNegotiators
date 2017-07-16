package kriate.production.com.gymnegotiators.binding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeMap;

import kriate.production.com.gymnegotiators.Model.Theme;

/**
 * Created by dima on 17.07.2017.
 */

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Theme> mThemes = new ArrayList<>();

    // Constructor
    public ImageAdapter(Context c,  TreeMap<String, Theme> themes) {
        mContext = c;
        mThemes.addAll(themes.values());
    }

    @Override
    public int getCount() {
        return mThemes.size();
    }

    @Override
    public Object getItem(int position) {
        return mThemes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(mThemes.get(position).getPhotoBit());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(120, 110));
        return imageView;
    }
}
