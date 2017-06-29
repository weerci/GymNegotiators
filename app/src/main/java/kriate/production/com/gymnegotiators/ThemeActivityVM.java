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

        selectedTheme.set(Loader.getThemies().get(0));
    }

    public final ObservableField<Theme> selectedTheme = new ObservableField<>();

}

