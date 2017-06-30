package kriate.production.com.gymnegotiators.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import kriate.production.com.gymnegotiators.R;

/**
 * Created by dima on 30.06.2017.
 */

public final class BindingAdapters {
    private BindingAdapters() {
        throw new AssertionError();
    }

    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.bg_image_holder)
                .into(view);
    }

    @BindingAdapter("app:onClick")
    public static void bindOnClick(View view, final Runnable runnable) {
        return;
        //view.setOnClickListener(v -> runnable.run());
    }
}
