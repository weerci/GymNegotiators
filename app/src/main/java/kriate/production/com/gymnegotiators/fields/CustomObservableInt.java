package kriate.production.com.gymnegotiators.fields;

/**
 * Created by dima on 29.06.2017.
 */

public class CustomObservableInt extends android.databinding.ObservableInt {

    public void increment() {
        set(get() + 1);
    }

    public void decrement() {
        set(get() - 1);
    }
}

