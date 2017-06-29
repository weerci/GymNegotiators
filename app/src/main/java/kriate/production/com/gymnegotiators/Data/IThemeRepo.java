package kriate.production.com.gymnegotiators.Data;

import kriate.production.com.gymnegotiators.Model.Theme;

/**
 * Created by dima on 29.06.2017.
 */

public interface IThemeRepo {

    void getTheme(Loader<Theme> loader);

    interface Loader<T> {
        void onLoaded(T t);
    }

}
