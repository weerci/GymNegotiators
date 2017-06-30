package kriate.production.com.gymnegotiators;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import java.util.ArrayList;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.binding.RecyclerBindingAdapter;
import kriate.production.com.gymnegotiators.binding.RecyclerConfiguration;
import kriate.production.com.gymnegotiators.fields.CustomObservableInt;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM  extends ActivityViewModel<ThemeActivity> {

    public ThemeActivityVM(ThemeActivity activity, String status) {
        super(activity);

        selectedTheme.set(themes.get(0));
        initRecycler();
    }

    public final RecyclerConfiguration recyclerConfiguration = new RecyclerConfiguration();
    private ArrayList<Theme> themes = Loader.getThemies();

    // Observable fields
    public final ObservableField<Theme> selectedTheme = new ObservableField<>();


    private static final int LAYOUT_HOLDER = R.layout.item_photo;
    private static final int PHOTOS_COLUMN_COUNT = 3;

    private void initRecycler() {
        RecyclerBindingAdapter<Integer> adapter = getAdapter();

        recyclerConfiguration.setLayoutManager(new GridLayoutManager(activity, PHOTOS_COLUMN_COUNT));
        recyclerConfiguration.setItemAnimator(new DefaultItemAnimator());
        recyclerConfiguration.setAdapter(adapter);
    }

    private RecyclerBindingAdapter<Integer> getAdapter() {
        
        ArrayList<Integer> photos = new ArrayList<Integer>();
        for (Theme item: themes) {
            photos.add(item.getPhotoId());
        }

        RecyclerBindingAdapter<Integer> adapter = new RecyclerBindingAdapter<>(LAYOUT_HOLDER, BR.photoId, photos);

        adapter.setOnItemClickListener((position, item)
                -> selectedTheme.set(themes.get(position)));
        return adapter;
    }

}

