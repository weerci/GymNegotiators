package kriate.production.com.gymnegotiators;

import android.databinding.ObservableField;

import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel;

import kriate.production.com.gymnegotiators.Model.Theme;

/**
 * Created by dima on 27.06.2017.
 */

public class ThemeActivityVM  extends ActivityViewModel<ThemeActivity> {

    public ThemeActivityVM(ThemeActivity activity) {
        super(activity);
    }

    public final ObservableField<Theme> theme = new ObservableField<>();
}
