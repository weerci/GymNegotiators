package kriate.production.com.gymnegotiators;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import kriate.production.com.gymnegotiators.Model.Theme;
import kriate.production.com.gymnegotiators.fields.CustomObservableInt;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM  extends ActivityViewModel<ThemeActivity> {

    public ThemeActivityVM(ThemeActivity activity, String status) {
        super(activity);

        theme.set(Demo.getThemies().get(0));
        photo.set("@drawable/boss");
    }

    public final ObservableField<Theme> theme = new ObservableField<>();
    public final ObservableField<String> photo = new ObservableField<>();

    public String getImageUrl() {
        // The URL will usually come from a model (i.e Profile)
        return "http://cdn.meme.am/instances/60677654.jpg";
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.list)
                .into(view);
    }
}

